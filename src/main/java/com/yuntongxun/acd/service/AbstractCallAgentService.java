package com.yuntongxun.acd.service;

import com.yuntongxun.acd.call.AbstractCallAgentProxy;
import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.CallAgentCallBackProxy;
import com.yuntongxun.acd.call.CallAgentResultHandle;
import com.yuntongxun.acd.queue.notification.QueueNotifyProxy;

import java.util.List;

public abstract class AbstractCallAgentService extends AbstractCallAgentProxy implements CallAgentCallBackProxy, QueueNotifyProxy {

    public AbstractCallAgentService() {
        super();
    }

    public AbstractCallAgentService(List<Agent> agents) {
        super(agents);
    }

    public void agree(String customerId) {
        callAgentManager.agentAgreeCall(customerId);
    }

    public void reject(String customerId) {
        callAgentManager.agentRejectCall(customerId);
    }
}
