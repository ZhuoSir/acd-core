package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.call.*;
import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;
import com.yuntongxun.acd.distribution.AgentDistributeProxy;
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
    private AgentDistributeProxy agentDistributeProxy;


    private ExecutorService taskPool;
    private final int CALLLISTENDEFAULTTIMEOUT = 10;
    private Map<String, CallAgentListenTask> callAgentListenTaskMap = new ConcurrentHashMap<>();

    private int callListenTimeout;

    public CustomerQueueManager() {
        taskPool = Executors.newCachedThreadPool();
        this.callListenTimeout = CALLLISTENDEFAULTTIMEOUT;
    }

    @Override
    public Agent workAfterLine(LineElement element) {
        Agent agent = null;
        Customer customer = (Customer) element ;
        ConferenceRoom conferenceRoom = agentDistributeProxy.distributeConference(customer);
        agent = conferenceRoom.getAgent();
        if (callAgentProxy != null) {
            callAgentProxy.call(conferenceRoom, this);
            CallAgentListenTask callAgentListenTask = new CallAgentListenTask(callListenTimeout, conferenceRoom, new CallAgentListenTask.ResponseCallBack() {

                    @Override
                    public void responsed(ConferenceRoom conferenceRoom) {
                        System.out.println("responsed");
                    }

                    @Override
                    public void notresponsed(ConferenceRoom conferenceRoom) {
                        callAgentProxy.callCancel(conferenceRoom);
                        lineFailed(conferenceRoom.getCustomer());
                    }
                });
            callAgentListenTaskMap.put(customer.getIndex(), callAgentListenTask);
            taskPool.submit(callAgentListenTask);
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
        this.processFinish(conferenceRoom.getCustomer());
        this.lineFailed(conferenceRoom.getCustomer());
    }

    @Override
    public void processFinish(LineElement element) {
        super.processFinish(element);
        Customer customer = (Customer) element;
        CallAgentListenTask callAgentListenTask = callAgentListenTaskMap.get(customer.getIndex());
        callAgentListenTask.setResponse(true);
        callAgentListenTaskMap.remove(customer.getIndex());
    }

    @Override
    public void queueAdjust(boolean isNotify, AcdQueue acdQueue) {
        Queue<LineElement> waitingQueue = acdQueue.getWaitingQueue();
        Iterator<LineElement> iterator = waitingQueue.iterator();
        taskPool.submit(new Runnable() {
            @Override
            public void run() {
                int preCount = 0;
                while (iterator.hasNext()) {
                    LineElement lineElement = iterator.next();
                    lineElement.setWaitingCount(preCount);
                    if (isNotify && queueNotifyProxy != null) {
                        QueueNotification queueNotification = new QueueNotification(lineElement, preCount, new Date(), 0);
                        taskPool.submit(new Runnable() {
                            @Override
                            public void run() {
                                queueNotifyProxy.sendNotification(queueNotification);
                            }
                        });
                    }
                    preCount++;
                }
            }
        });

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
//        this.processFinish(conferenceRoom.getCustomer()) ;
    }

    @Override
    public void callFailed(ConferenceRoom conferenceRoom) {
        Customer customer =conferenceRoom.getCustomer();
        this.lineFailed(customer);
        CallAgentListenTask callAgentListenTask = callAgentListenTaskMap.get(customer.getIndex());
        callAgentListenTask.setInValidEnd(true);
        callAgentListenTaskMap.remove(customer.getIndex());
    }

    @Override
    public void callError(ConferenceRoom conferenceRoom, Exception e) {
        Customer customer = conferenceRoom.getCustomer();
        CallAgentListenTask callAgentListenTask = callAgentListenTaskMap.get(customer.getIndex());
        callAgentListenTask.setInValidEnd(true);
        callAgentListenTaskMap.remove(customer.getIndex());
    }

    public void setCallListenTimeout(int callListenTimeout) {
        if (callListenTimeout > 0) {
            this.callListenTimeout = callListenTimeout;
        }
    }

    public void setAgentDistributeProxy(AgentDistributeProxy agentDistributeProxy) {
        this.agentDistributeProxy = agentDistributeProxy;
    }
}
