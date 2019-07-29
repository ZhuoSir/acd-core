package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.queue.bean.LineElement;
import com.yuntongxun.acd.queue.bean.QueueInfo;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AcdQueue {

    // 等待队列
    protected BlockingQueue<LineElement> waitingQueue;
    // 处理失败队列
    protected Queue<LineElement> processFailedQueue;
    // 优先队列
    protected Queue<LineElement> priorityQueue;

    public AcdQueue() {
        waitingQueue = new LinkedBlockingQueue<LineElement>();
        priorityQueue = new ConcurrentLinkedQueue<>();
        processFailedQueue = new ConcurrentLinkedQueue<>();
    }

    public void add(LineElement element) {
        waitingQueue.add(element);
    }

    public void addPriority(LineElement element) {
        priorityQueue.add(element);
    }

    public void addProcessFailed(LineElement element) {
        processFailedQueue.add(element);
    }

    public void remove(LineElement element) {
        waitingQueue.remove(element);
        priorityQueue.remove(element);
        processFailedQueue.remove(element);
    }

    public BlockingQueue<LineElement> getWaitingQueue() {
        return waitingQueue;
    }

    public Queue<LineElement> getProcessFailedQueue() {
        return processFailedQueue;
    }

    public Queue<LineElement> getPriorityQueue() {
        return priorityQueue;
    }

    public int waitingSize() {
        return waitingQueue.size();
    }

    public int prioritySize() {
        return priorityQueue.size();
    }

    public int failedSize() {
        return processFailedQueue.size();
    }

    @Override
    public String toString() {
        return "AcdQueue{" +
                "waitingQueue=" + waitingQueue +
                ", processFailedQueue=" + processFailedQueue +
                ", priorityQueue=" + priorityQueue +
                '}';
    }

    public String detail() {
        String string = "waiting : " + waitingQueue.size() + " priority : " + priorityQueue.size() + " processFailed : " + processFailedQueue.size();
        return string;
    }

    public QueueInfo getQueueInfo() {
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setWaitingCount(waitingQueue.size());
        return queueInfo;
    }
}
