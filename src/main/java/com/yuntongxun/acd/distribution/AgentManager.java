package com.yuntongxun.acd.distribution;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AgentManager {

    private AgentDistribute agentDistribute;

    protected Map<String, ConferenceRoom> conferenceRoomPool;


    public AgentManager() {
        conferenceRoomPool = new ConcurrentHashMap<>();
        agentDistribute = new BlockingQueueAgentDistributor();
    }

    public void setAgentDistribute(AgentDistribute agentDistribute) {
        this.agentDistribute = agentDistribute;
    }

    public void putAgentQueue(Collection<Agent> agentQueue) {
       agentDistribute.addAgent(agentQueue);
    }

    public void putAgentQueue(Agent agent) {
        agentDistribute.addAgent(agent);
    }

    public void removeAgentQueue(String account) {
        agentDistribute.removeAgent(account);
    }

    public void dismissConferenceRoom(ConferenceRoom conferenceRoom) {
//        putAgentQueue(conferenceRoom.getAgent());
        conferenceRoomPool.remove(conferenceRoom.getCustomer().getAccount());
    }

    public Collection<Agent> getAgents() {
        return agentDistribute.agentList();
    }

    public Agent distributedAgent(String customerId) {
        ConferenceRoom conferenceRoom = conferenceRoomPool.get(customerId);
        return null != conferenceRoom ? conferenceRoom.getAgent() : null;
    }

    public ConferenceRoom distributeAgent(Customer customer) {
        Agent agent = agentDistribute.distribute(customer);
        if (agent != null) {
            ConferenceRoom conferenceRoom = new ConferenceRoom(agent, customer);
            conferenceRoomPool.put(customer.index(), conferenceRoom);
            return conferenceRoom;
        }
        return null;
    }
}
