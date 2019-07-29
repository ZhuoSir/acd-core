package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;
import java.util.List;

public abstract class AbstractCallAgentProxy implements CallAgentProxy, CallAgentAction {

    protected AgentManager agentManager;
    protected CallAgentManager callAgentManager;

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

    public void removeAgent(Agent agent) {
        agentManager.removeAgentQueue(agent);
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
    public boolean call(Customer customer, CallAgentCallBack callAgentCallBack) {

        ConferenceRoom conferenceRoom = agentManager.distributeAgent(customer);
        if (null != conferenceRoom) {
            callAgentManager.createCallTask(conferenceRoom, this, callAgentCallBack);
            return true;
        }
        return false;
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
}
