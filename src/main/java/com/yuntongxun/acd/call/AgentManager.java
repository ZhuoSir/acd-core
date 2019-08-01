package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class AgentManager {

    protected BlockingQueue<Agent> agentQueue;

    protected Map<String, ConferenceRoom> conferenceRoomPool;

    protected Map<String, Agent> agentMap;

    public AgentManager() {
        agentQueue = new LinkedBlockingQueue<>();
        conferenceRoomPool = new ConcurrentHashMap<>();
        agentMap = new ConcurrentHashMap<>();
    }

    public void putAgentQueue(Collection<Agent> agentQueue) {
        this.agentQueue.addAll(agentQueue);
        for (Agent agent : agentQueue) {
            agentMap.put(agent.getAccount(), agent);
        }
    }

    public void putAgentQueue(Agent agent) {
        this.agentQueue.add(agent);
        agentMap.put(agent.getAccount(), agent);
    }

    public void removeAgentQueue(String account) {
        Agent agent = agentMap.get(account);
        this.agentQueue.remove(agent);
    }

    public Agent getAgent(String account) {
        return agentMap.get(account);
    }

    public BlockingQueue<Agent> getAgentQueue() {
        return agentQueue;
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
