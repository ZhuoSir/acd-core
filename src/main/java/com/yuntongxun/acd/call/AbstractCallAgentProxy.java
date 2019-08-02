package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public abstract class  AbstractCallAgentProxy implements CallAgentProxy, CallAgentAction {

    protected AgentManager agentManager;
    protected CallAgentManager callAgentManager;

    private CallAgentResultHandle callAgentResultHandle;

    public AbstractCallAgentProxy() {
        agentManager = new AgentManager();
        callAgentManager = new CallAgentManager();
    }

    public AbstractCallAgentProxy(List<Agent> agents) {
        agentManager = new AgentManager();
        callAgentManager = new CallAgentManager();
        if (agents != null) {
            agentManager.putAgentQueue(agents);
        }
    }

    public void putAgent(Agent agent) {
        agentManager.putAgentQueue(agent);
    }

    public void putAgent(Collection<Agent> agents) {
        agentManager.putAgentQueue(agents);
    }

    public void removeAgent(String account) {
        agentManager.removeAgentQueue(account);
    }

    public BlockingQueue<Agent> getAgentQueue() {
        return agentManager.getAgentQueue();
    }

    public void setAgentManager(AgentManager agentManager) {
        this.agentManager = agentManager;
    }

    public void setCallAgentManager(CallAgentManager callAgentManager) {
        this.callAgentManager = callAgentManager;
    }

    public AgentManager getAgentManager() {
        return agentManager;
    }

    @Override
    public ConferenceRoom call(Customer customer, CallAgentCallBack callAgentCallBack) {

        ConferenceRoom conferenceRoom = agentManager.distributeAgent(customer);
        if (null != conferenceRoom) {
            callAgentManager.createCallTask(conferenceRoom, this, callAgentCallBack);
        }
        return conferenceRoom;
    }

    @Override
    public void callFinish(Agent agent) {
        if (agent.getStatus() != Agent.DENY)
            agentManager.putAgentQueue(agent);
    }

    @Override
    public void callCancel(Customer customer, Agent agent) {
        if (agent.getStatus() != Agent.DENY)
            agentManager.putAgentQueue(agent);
    }

    public void setCallAgentResultHandle(CallAgentResultHandle callAgentResultHandle) {
        this.callAgentResultHandle = callAgentResultHandle;
        this.callAgentManager.setLastOneCallAgentResultHandle(callAgentResultHandle);
    }

    public List<CallFailedDetail> getFailedCallAgentList() {
        return callAgentManager.getFailedList();
    }

    public void startCallListenThread() {

    }
}
