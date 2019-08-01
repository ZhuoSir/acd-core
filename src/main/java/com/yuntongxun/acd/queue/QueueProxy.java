package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.bean.LineElement;

public interface QueueProxy {

    /**
     * 队列通知
     *
     * */
    void queueNotify(AcdQueue acdQueue);

    /**
     * 分配通知
     *
     * */
    void distributeNotify(LineElement lineElement, Agent agent);
}
