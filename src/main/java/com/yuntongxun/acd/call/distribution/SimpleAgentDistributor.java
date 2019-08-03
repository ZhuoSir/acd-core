package com.yuntongxun.acd.call.distribution;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class SimpleAgentDistributor implements AgentDistribute {

    private BlockingQueue<Agent> agentQueue;

    private Map<String, Agent> agentMap;

    @Override
    public Agent distribute(Customer customer) {
        try {
            Agent agent = agentQueue.take();
            agent.setStatus(Agent.BUSY);
            return agent;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addAgent(Agent agent) {
        this.agentQueue.add(agent);
        agentMap.put(agent.getAccount(), agent);
    }

    @Override
    public void addAgent(Collection<Agent> agents) {
        this.agentQueue.addAll(agents);
        for (Agent agent : agents) {
            agentMap.put(agent.getAccount(), agent);
        }
    }

    @Override
    public void removeAgent(String agentAccount) {
        Agent agent = agentMap.remove(agentAccount);
        this.agentQueue.remove(agent);
    }

    @Override
    public Collection<Agent> agentList() {
        return agentQueue;
    }
}
