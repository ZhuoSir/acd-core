package com.yuntongxun.acd.call;

import com.yuntongxun.acd.queue.bean.Customer;

public interface CallAgentProxy {

    boolean call(Customer customer, CallAgentCallBack callAgentCallBack);

}
