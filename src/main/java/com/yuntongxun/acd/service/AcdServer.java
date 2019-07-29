package com.yuntongxun.acd.service;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.AcdQueue;
import com.yuntongxun.acd.queue.CustomerQueueManager;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.bean.QueueInfo;

import java.util.Collection;

public class AcdServer {

    private AcdQueue acdQueue;

    private CustomerQueueManager customerQueueManager;

    private AbstractCallAgentService callAgentService;

    public void init(boolean isNotify) {
        acdQueue = new AcdQueue();
        customerQueueManager = new CustomerQueueManager();
        customerQueueManager.setAcdQueue(acdQueue);
        if (callAgentService != null) {
            customerQueueManager.setQueueNotifyProxy(callAgentService);
            customerQueueManager.setCallAgentCallBackProxy(callAgentService);
            customerQueueManager.setCallAgentProxy(callAgentService);
        }
        if (isNotify)
            customerQueueManager.notifySwitchOn();
        else
            customerQueueManager.notifySwitchOff();
    }


    public void start() {
        if (null == customerQueueManager) return;
        customerQueueManager.start();
    }

    public QueueInfo line(Customer customer) {
        if (null == customerQueueManager) return null;
        return customerQueueManager.line(customer);
    }

    public void linePriority(Customer customer) {
        if (null == customerQueueManager) return;
        customerQueueManager.linePriority(customer);
    }

    public void cancelLine(Customer customer) {
        if (null == customerQueueManager) return;
        customerQueueManager.cancelLine(customer);
    }

    public void rejectCall(String customerId) {
        if (null == callAgentService) return;
        callAgentService.reject(customerId);
    }

    public void agreeCall(String customerId) {
        if (null == callAgentService) return;
        callAgentService.agree(customerId);
    }

    public void addAgents(Collection<Agent> agents) {
        if (null == callAgentService) return;
        callAgentService.putAgent(agents);
    }

    public void addAgent(Agent agent) {
        if (null == callAgentService) return;
        callAgentService.putAgent(agent);
    }

    public void removeAgent(Agent agent) {
        if (null == callAgentService) return;
        callAgentService.removeAgent(agent);
    }

    public void setCallAgentService(AbstractCallAgentService callAgentService) {
        this.callAgentService = callAgentService;
        if (customerQueueManager != null) {
            customerQueueManager.setQueueNotifyProxy(callAgentService);
            customerQueueManager.setCallAgentCallBackProxy(callAgentService);
            customerQueueManager.setCallAgentProxy(callAgentService);
        }
    }
}
