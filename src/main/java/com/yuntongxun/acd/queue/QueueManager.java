package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.queue.bean.LineElement;
import com.yuntongxun.acd.queue.bean.LineElementInfo;

public interface QueueManager {

    AcdQueue getAcdQueue();

    LineElementInfo line(LineElement element);

    void linePriority(LineElement element);

    void lineProcess();

    void lineFailed(LineElement element);

    void cancelLine(LineElement element);

    void processFinish(LineElement element);

    void queueAdjust(boolean isNotify);
}
