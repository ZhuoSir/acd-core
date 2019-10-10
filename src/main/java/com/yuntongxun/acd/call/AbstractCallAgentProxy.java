package com.yuntongxun.acd.call;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;
import com.yuntongxun.acd.distribution.AgentDistribute;
import com.yuntongxun.acd.distribution.AgentDistributeProxy;
import com.yuntongxun.acd.distribution.AgentManager;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;
import java.util.List;

public abstract class  AbstractCallAgentProxy implements CallAgentProxy, CallAgentAction, AgentDistributeProxy {

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
    public void call(ConferenceRoom conferenceRoom, CallAgentCallBack callAgentCallBack) {
        if (null != conferenceRoom) {
            callAgentManager.createCallTask(conferenceRoom, this, callAgentCallBack);
        }
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

    @Override
    public ConferenceRoom distributeConference(Agent agent, Customer customer) {
        ConferenceRoom conferenceRoom = null;
        if (agent == null) {
            conferenceRoom = agentManager.distributeAgent(customer);
        } else {
            conferenceRoom = agentManager.distributeConferenceRoom(agent, customer);
        }
        return conferenceRoom;
    }

    @Override
    public Agent distributeAgent() {
        return agentManager.distributeAgent();
    }
}
