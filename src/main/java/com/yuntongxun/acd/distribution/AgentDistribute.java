package com.yuntongxun.acd.distribution;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;

public interface AgentDistribute {

    Agent distribute();

    Agent distribute(Customer customer);

    void addAgent(Agent agent);

    void addAgent(Collection<Agent> agent);

    void removeAgent(String agentAccount);

    Collection<Agent> agentList();

}
