package com.yuntongxun.example;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.bean.LineElementInfo;
import com.yuntongxun.acd.AbstractCallAgentService;
import com.yuntongxun.acd.AcdServer;

import java.util.ArrayList;
import java.util.List;

public class AcdMain {

    public static void main(String[] args) {

        List<Agent> agents = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Agent agent = new Agent(i);
            agent.setAccount("agent" + i);
            agents.add(agent);
        }
        AbstractCallAgentService callAgentService = new CallAgentService(agents);

        AcdServer acdServer = new AcdServer();
        acdServer.setCallAgentService(callAgentService);
        acdServer.setNotify(1);
        acdServer.start();

        for (int i = 0; i < 30; i++) {
//            Thread.sleep(1000);
//            if (i % 5 == 0) {
//                customerQueueManager.linePriority(new Customer(i));
//            } else {
//                customerQueueManager.line(new Customer(i));
//            }
            Customer customer = new Customer(i);
            customer.setAccount("ceshi" + i);
            LineElementInfo lineElementInfo = acdServer.line(customer);
            System.out.println(lineElementInfo);
        }
    }

}
