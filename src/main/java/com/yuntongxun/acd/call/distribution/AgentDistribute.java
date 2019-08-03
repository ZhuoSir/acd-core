package com.yuntongxun.acd.call.distribution;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;

public interface AgentDistribute {

    Agent distribute(Customer customer);

    void addAgent(Agent agent);

    void addAgent(Collection<Agent> agent);

    void removeAgent(String agentAccount);

    Collection<Agent> agentList();

}
