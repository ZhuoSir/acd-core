package com.yuntongxun.acd.queue.notification;

import com.yuntongxun.acd.queue.AcdQueue;
import com.yuntongxun.acd.queue.QueueProxy;
import com.yuntongxun.acd.queue.bean.LineElement;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class NotificationManager implements QueueProxy {

    private QueueNotifyProxy queueNotifyProxy;

    @Override
    public void queueNotify(AcdQueue acdQueue) {
//        final int waitingQueueSize = acdQueue.waitingSize();
//        final int priorityQueueSize = acdQueue.prioritySize();
//        final int failedQueueSize = acdQueue.failedSize();

        //队列总人数
//        int allQueueCount = waitingQueueSize + priorityQueueSize + failedQueueSize;

//        Queue<LineElement> waitingQueue = acdQueue.getWaitingQueue();
//        Iterator<LineElement> iterator = waitingQueue.iterator();
//        int preCount = 0;
//        while (iterator.hasNext()) {
//            LineElement lineElement = iterator.next();
//            QueueNotification queueNotification = new QueueNotification(lineElement, preCount, new Date());
//            queueNotifyProxy.sendNotification(queueNotification);
//            preCount++;
//        }
    }

    public void setQueueNotifyProxy(QueueNotifyProxy queueNotifyProxy) {
        this.queueNotifyProxy = queueNotifyProxy;
    }
}
