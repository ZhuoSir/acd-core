package com.yuntongxun.acd.service;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.call.CallFailedDetail;
import com.yuntongxun.acd.queue.AcdQueue;
import com.yuntongxun.acd.queue.CustomerQueueManager;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.bean.LineElementInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AcdServer {

    private int notify;

    private AcdQueue acdQueue;

    private CustomerQueueManager customerQueueManager;

    private AbstractCallAgentService callAgentService;

    private AtomicInteger cindex;

    private Map<String, Customer> customerMap = new ConcurrentHashMap<>();

    public void init() {
        acdQueue = new AcdQueue();
        customerQueueManager = new CustomerQueueManager();
        customerQueueManager.setAcdQueue(acdQueue);
        if (callAgentService != null) {
            customerQueueManager.setQueueNotifyProxy(callAgentService);
            customerQueueManager.setCallAgentCallBackProxy(callAgentService);
            customerQueueManager.setCallAgentProxy(callAgentService);
            callAgentService.setCallAgentResultHandle(customerQueueManager);
        }
        if (notify == 1)
            customerQueueManager.notifySwitchOn();
        else
            customerQueueManager.notifySwitchOff();

        cindex = new AtomicInteger(0);
    }


    public void start() {
        if (null == customerQueueManager) return;
        customerQueueManager.start();
    }

    public LineElementInfo line(Customer customer) {
        if (null == customerQueueManager) return null;
        customer.setIndex(cindex.getAndIncrement());
        LineElementInfo lineElementInfo = customerQueueManager.line(customer);
        customerMap.put(customer.index(), customer);
        lineElementInfo.setIndex(customer.index());
        return lineElementInfo;
    }

    public void linePriority(Customer customer) {
        if (null == customerQueueManager) return;
        customerQueueManager.linePriority(customer);
    }

    public void cancelLine(String cindex) {
        if (null == customerQueueManager) return;
        Customer customer = customerMap.get(cindex);
        customerQueueManager.cancelLine(customer);
    }

    public void rejectCall(String cindex) {
        if (null == callAgentService) return;
        callAgentService.reject(cindex);
    }

    public void agreeCall(String cindex) {
        if (null == callAgentService) return;
        callAgentService.agree(cindex);
        customerMap.remove(cindex);
    }

    public void addAgents(Collection<Agent> agents) {
        if (null == callAgentService) return;
        callAgentService.putAgent(agents);
    }

    public void addAgent(Agent agent) {
        if (null == callAgentService) return;
        callAgentService.putAgent(agent);
    }

    public void removeAgent(String account) {
        if (null == callAgentService) return;
        callAgentService.removeAgent(account);
    }

    public List<CallFailedDetail> getFailedCallAgent() {
        if (null == callAgentService) return null;
        return callAgentService.getFailedCallAgentList();
    }

    public AcdQueue queueInfo() {
        return acdQueue;
    }

    public BlockingQueue<Agent> getAgentQueue() {
        return callAgentService.getAgentQueue();
    }

    public void setCallAgentService(AbstractCallAgentService callAgentService) {
        this.callAgentService = callAgentService;
        if (customerQueueManager != null) {
            customerQueueManager.setQueueNotifyProxy(callAgentService);
            customerQueueManager.setCallAgentCallBackProxy(callAgentService);
            customerQueueManager.setCallAgentProxy(callAgentService);
            callAgentService.setCallAgentResultHandle(customerQueueManager);
        }
    }

    public void setNotify(int notify) {
        this.notify = notify;
    }
}
