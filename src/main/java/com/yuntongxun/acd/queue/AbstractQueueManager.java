package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.queue.bean.LineElement;
import com.yuntongxun.acd.queue.bean.QueueInfo;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractQueueManager extends Thread implements QueueManager {

    public AbstractQueueManager() {
        super("LineThread");
    }

    private AcdQueue acdQueue;

    private QueueProxy queueProxy;

    private boolean notifySwitch = false;

    public void notifySwitchOn() {
        this.notifySwitch = true;
    }

    public void notifySwitchOff() {
        this.notifySwitch = false;
    }

    public void setAcdQueue(AcdQueue acdQueue) {
        this.acdQueue = acdQueue;
    }

    public void setQueueProxy(QueueProxy queueProxy) {
        this.queueProxy = queueProxy;
    }

    @Override
    public AcdQueue getAcdQueue() {
        return acdQueue;
    }

    @Override
    public QueueInfo line(LineElement element) {
        acdQueue.add(element);
        return acdQueue.getQueueInfo();
    }

    @Override
    public void linePriority(LineElement element) {
        acdQueue.addPriority(element);
    }

    @Override
    public void lineFailed(LineElement element) {
        acdQueue.addProcessFailed(element);
    }

    @Override
    public void cancelLine(LineElement element) {
        acdQueue.remove(element);
    }

    public void processFinish(LineElement element) {
    }

    @Override
    public void queueNotify() {
        queueProxy.queueNotify(acdQueue);
    }

    @Override
    public void run() {
        super.run();
        lineProcess();
    }

    @Override
    public void lineProcess() {
        try {
            for (;;) {
                AcdQueue acdQueue = getAcdQueue();
                BlockingQueue<LineElement> waitingQueue  = acdQueue.getWaitingQueue();
                Queue<LineElement> priorityQueue         = acdQueue.getPriorityQueue();
                Queue<LineElement> processFailedQueue    = acdQueue.getProcessFailedQueue();

                LineElement element = processFailedQueue.poll();
                if (null == element) {
                    element = priorityQueue.poll();
                    if (null == element) {
                        element = waitingQueue.poll(2, TimeUnit.SECONDS);
                    }
                }

                if (null != element) {
                    if (workAfterLine(element)) {
                        if (notifySwitch) {
                            queueNotify();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public abstract boolean workAfterLine(LineElement element);
}
