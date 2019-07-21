package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.call.CallAgentCallBack;
import com.yuntongxun.acd.call.CallAgentCallBackProxy;
import com.yuntongxun.acd.call.CallAgentProxy;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.bean.LineElement;

public class CustomerQueueManager extends AbstractQueueManager implements CallAgentCallBack {

    private CallAgentProxy callAgentProxy;
    private CallAgentCallBackProxy callAgentCallBackProxy;

    @Override
    public boolean workAfterLine(LineElement element) {
        Customer customer = (Customer) element;
        if (null != callAgentProxy) {
            return callAgentProxy.call(customer, this);
        }
        return false;
    }

    public void setCallAgentProxy(CallAgentProxy callAgentProxy) {
        this.callAgentProxy = callAgentProxy;
    }

    public void setCallAgentCallBackProxy(CallAgentCallBackProxy callAgentCallBackProxy) {
        this.callAgentCallBackProxy = callAgentCallBackProxy;
    }

    @Override
    public void agreeCall(ConferenceRoom conferenceRoom) {
        this.callAgentCallBackProxy.agreeCall(conferenceRoom.getCustomer(), conferenceRoom.getAgent());
        this.processFinish(conferenceRoom.getCustomer());
    }

    @Override
    public void rejectCall(ConferenceRoom conferenceRoom) {
        this.callAgentCallBackProxy.rejectCall(conferenceRoom.getCustomer(), conferenceRoom.getAgent());
        this.lineFailed(conferenceRoom.getCustomer());
        this.processFinish(conferenceRoom.getCustomer());
    }
}
