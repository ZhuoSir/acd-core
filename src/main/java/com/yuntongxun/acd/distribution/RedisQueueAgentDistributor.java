package com.yuntongxun.acd.distribution;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;

public class RedisQueueAgentDistributor extends AbstractAgentDistributor {

    @Override
    public Agent distribute() {
        return null;
    }

    @Override
    public Agent distribute(Customer customer) {
        return null;
    }

    @Override
    public void addAgent(Agent agent) {

    }

    @Override
    public void addAgent(Collection<Agent> agent) {

    }

    @Override
    public void removeAgent(String agentAccount) {

    }

    @Override
    public Collection<Agent> agentList() {
        return null;
    }
}
