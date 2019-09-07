package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.LineElement;

public interface QueueProxy {

    /**
     * 队列调整
     *
     * */
    void queueAdjust(boolean isNotify, AcdQueue acdQueue);

    /**
     * 分配通知
     *
     * */
    void distributeNotify(LineElement lineElement, Agent agent);
}
