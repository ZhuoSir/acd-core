package com.yuntongxun.acd.queue.notification;

import com.yuntongxun.acd.queue.bean.LineElement;

public interface QueueNotifyProxy {

    void sendNotification(QueueNotification queueNotification);

}
