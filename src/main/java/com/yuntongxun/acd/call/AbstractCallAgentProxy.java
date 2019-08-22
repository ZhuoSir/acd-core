package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.call.distribution.AgentDistribute;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public abstract class  AbstractCallAgentProxy implements CallAgentProxy, CallAgentAction {

    protected AgentManager agentManager;
    protected CallAgentManager callAgentManager;

    public AbstractCallAgentProxy() {
        agentManager = new AgentManager();
        callAgentManager = new CallAgentManager();
        callAgentManager.setAgentManager(agentManager);
    }

    public AbstractCallAgentProxy(List<Agent> agents) {
        agentManager = new AgentManager();
        callAgentManager = new CallAgentManager();
        callAgentManager.setAgentManager(agentManager);
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

    public Collection<Agent> getAgentQueue() {
        return agentManager.getAgents();
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
    public void callCancel(ConferenceRoom conferenceRoom) {
        callCancel(conferenceRoom.getAgent());
    }

    @Override
    public void callFinish(Customer customer, Agent agent) {
        agentManager.putAgentQueue(agent);
        ConferenceRoom conferenceRoom = agentManager.distributeAgent(customer);
        agentManager.dismissConferenceRoom(conferenceRoom);
    }

    @Override
    public void callCancel(Agent agent) {
        agentManager.putAgentQueue(agent);
    }

    public void setCallAgentResultHandle(CallAgentResultHandle callAgentResultHandle) {
        this.callAgentManager.setLastOneCallAgentResultHandle(callAgentResultHandle);
    }

    public List<CallFailedDetail> getFailedCallAgentList() {
        return callAgentManager.getFailedList();
    }

    public void setAgentDistributor(AgentDistribute agentDistributor) {
        agentManager.setAgentDistribute(agentDistributor);
    }
}
