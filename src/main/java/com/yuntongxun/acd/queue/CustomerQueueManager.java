package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.call.CallAgentCallBack;
import com.yuntongxun.acd.call.CallAgentCallBackProxy;
import com.yuntongxun.acd.call.CallAgentProxy;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.bean.LineElement;
import com.yuntongxun.acd.queue.notification.QueueNotification;
import com.yuntongxun.acd.queue.notification.QueueNotifyProxy;

import java.util.Date;
import java.util.Iterator;
import java.util.Queue;

public class CustomerQueueManager extends AbstractQueueManager implements CallAgentCallBack, QueueProxy {

    private CallAgentProxy callAgentProxy;
    private CallAgentCallBackProxy callAgentCallBackProxy;
    private QueueNotifyProxy queueNotifyProxy;

    @Override
    public Agent workAfterLine(LineElement element) {
        Customer customer = (Customer) element;
        if (null != callAgentProxy) {
            return callAgentProxy.call(customer, this).getAgent();
        }
        return null;
    }

    public void setCallAgentProxy(CallAgentProxy callAgentProxy) {
        this.callAgentProxy = callAgentProxy;
    }

    public void setCallAgentCallBackProxy(CallAgentCallBackProxy callAgentCallBackProxy) {
        this.callAgentCallBackProxy = callAgentCallBackProxy;
    }

    public void setQueueNotifyProxy(QueueNotifyProxy queueNotifyProxy) {
        this.queueNotifyProxy = queueNotifyProxy;
        super.setQueueProxy(this);
    }

    @Override
    public void agreeCall(ConferenceRoom conferenceRoom) {
        if (callAgentCallBackProxy == null) return;
        this.callAgentCallBackProxy.agreeCall(conferenceRoom.getCustomer(), conferenceRoom.getAgent());
        this.processFinish(conferenceRoom.getCustomer());
    }

    @Override
    public void rejectCall(ConferenceRoom conferenceRoom) {
        if (callAgentCallBackProxy == null) return;
        this.callAgentCallBackProxy.rejectCall(conferenceRoom.getCustomer(), conferenceRoom.getAgent());
        this.lineFailed(conferenceRoom.getCustomer());
        this.processFinish(conferenceRoom.getCustomer());
    }

    @Override
    public void queueNotify(AcdQueue acdQueue) {
        if (null == queueNotifyProxy) return;
        Queue<LineElement> waitingQueue = acdQueue.getWaitingQueue();
        Iterator<LineElement> iterator = waitingQueue.iterator();
        int preCount = 0;
        while (iterator.hasNext()) {
            LineElement lineElement = iterator.next();
            QueueNotification queueNotification = new QueueNotification(lineElement, preCount, new Date(), 0);
            queueNotifyProxy.sendNotification(queueNotification);
            preCount++;
        }
    }

    @Override
    public void distributeNotify(LineElement lineElement, Agent agent) {
        if (null == queueNotifyProxy) return;
        QueueNotification queueNotification = new QueueNotification(lineElement, 0, new Date(), 1);
        queueNotification.setDistributedAgent(agent);
        queueNotifyProxy.sendNotification(queueNotification);
    }
}
