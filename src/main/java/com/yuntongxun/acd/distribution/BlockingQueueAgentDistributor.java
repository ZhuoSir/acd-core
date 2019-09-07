package com.yuntongxun.acd.distribution;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueAgentDistributor extends AbstractAgentDistributor {

    // 坐席分配队列
    private BlockingQueue<Agent> agentQueue;

    // 坐席表
    private Map<String, Agent> agentMap;

    public BlockingQueueAgentDistributor() {
        agentMap = new ConcurrentHashMap<>();
        agentQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public Agent distribute(Customer customer) {
        try {
            Agent agent = agentQueue.take();
            agent.setStatus(Agent.BUSY);
            agentMap.remove(agent.getAccount());
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
