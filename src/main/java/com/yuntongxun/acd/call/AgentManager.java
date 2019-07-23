package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class AgentManager {

    protected BlockingQueue<Agent> agentQueue;

    protected Map<String, ConferenceRoom> conferenceRoomPool;

    public AgentManager() {
        agentQueue = new LinkedBlockingQueue<>();
        conferenceRoomPool = new ConcurrentHashMap<>();
    }

    public void setAgentQueue(BlockingQueue<Agent> agentQueue) {
        this.agentQueue = agentQueue;
    }

    public void putAgentQueue(List<Agent> agentQueue) {
        this.agentQueue.addAll(agentQueue);
    }

    public void putAgentQueue(Agent agent) {
        this.agentQueue.add(agent);
    }

    public Agent distributedAgent(String customerId) {
        ConferenceRoom conferenceRoom = conferenceRoomPool.get(customerId);
        return null != conferenceRoom ? conferenceRoom.getAgent() : null;
    }

    public ConferenceRoom distributeAgent(Customer customer) {
        try {
            Agent agent = agentQueue.take();
            agent.setStatus(Agent.BUSY);
            ConferenceRoom conferenceRoom = new ConferenceRoom(agent, customer);
            conferenceRoomPool.put(customer.getId(), conferenceRoom);
            return conferenceRoom;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
