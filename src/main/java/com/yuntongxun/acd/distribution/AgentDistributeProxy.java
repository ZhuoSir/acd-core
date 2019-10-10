package com.yuntongxun.acd.distribution;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

public interface AgentDistributeProxy {

    ConferenceRoom distributeConference(Agent agent, Customer customer);

    Agent distributeAgent();
}
