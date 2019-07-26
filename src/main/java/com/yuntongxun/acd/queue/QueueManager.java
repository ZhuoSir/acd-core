package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.queue.bean.LineElement;
import com.yuntongxun.acd.queue.bean.QueueInfo;

public interface QueueManager {

    AcdQueue getAcdQueue();

    QueueInfo line(LineElement element);

    void linePriority(LineElement element);

    void lineProcess();

    void lineFailed(LineElement element);

    void processFinish(LineElement element);

    void queueNotify();
}
