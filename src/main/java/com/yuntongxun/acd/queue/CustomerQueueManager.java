package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.call.*;
import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.bean.LineElement;
import com.yuntongxun.acd.queue.notification.QueueNotification;
import com.yuntongxun.acd.queue.notification.QueueNotifyProxy;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerQueueManager extends AbstractQueueManager implements CallAgentCallBack, QueueProxy, CallAgentResultHandle {

    private CallAgentProxy callAgentProxy;
    private CallAgentCallBackProxy callAgentCallBackProxy;
    private QueueNotifyProxy queueNotifyProxy;

    private ExecutorService taskPool;
    private final int callListenTimeout = 10;
    private Map<String, CallAgentListenTask> callAgentListenTaskMap = new ConcurrentHashMap<>();

    public CustomerQueueManager() {
        taskPool = Executors.newFixedThreadPool(3);
    }

    @Override
    public Agent workAfterLine(LineElement element) {
        Agent agent = null;
        Customer customer = (Customer) element;
        if (null != callAgentProxy) {
            ConferenceRoom conferenceRoom = callAgentProxy.call(customer, this);
            CallAgentListenTask callAgentListenTask = new CallAgentListenTask(callListenTimeout, conferenceRoom, new CallAgentListenTask.ResponseCallBack() {
                @Override
                public void responsed(ConferenceRoom conferenceRoom) {
                    // 已响应，不处理；
                }

                @Override
                public void notresponsed(ConferenceRoom conferenceRoom) {
                    // 未响应
                    System.out.println(conferenceRoom + " not responsed ");
                    lineFailed(conferenceRoom.getCustomer());
//                    processFinish(conferenceRoom.getCustomer());
                }
            });
            callAgentListenTaskMap.put(customer.getIndex(), callAgentListenTask);
            taskPool.submit(callAgentListenTask);
            agent = conferenceRoom.getAgent();
        }
        return agent;
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
    public void processFinish(LineElement element) {
        super.processFinish(element);
        Customer customer = (Customer) element;
        CallAgentListenTask callAgentListenTask = callAgentListenTaskMap.get(customer.getIndex());
        callAgentListenTask.setResponse(true);
        callAgentListenTaskMap.put(customer.getIndex(), callAgentListenTask);
    }

    @Override
    public void queueNotify(AcdQueue acdQueue) {
        if (null == queueNotifyProxy) return;
        Queue<LineElement> waitingQueue = acdQueue.getWaitingQueue();
        Iterator<LineElement> iterator = waitingQueue.iterator();
        int preCount = 0;
        while (iterator.hasNext()) {
            LineElement lineElement = iterator.next();
            lineElement.setWaitingCount(preCount);
            QueueNotification queueNotification = new QueueNotification(lineElement, preCount, new Date(), 0);
            taskPool.submit(new Runnable() {
                @Override
                public void run() {
                    queueNotifyProxy.sendNotification(queueNotification);
                }
            });
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

    @Override
    public void callSuccess(ConferenceRoom conferenceRoom) {
        return;
    }

    @Override
    public void callFailed(ConferenceRoom conferenceRoom) {
        this.lineFailed(conferenceRoom.getCustomer());
        this.processFinish(conferenceRoom.getCustomer());
    }
}
