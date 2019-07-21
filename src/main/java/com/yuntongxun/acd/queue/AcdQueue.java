package com.yuntongxun.acd.queue;

import com.yuntongxun.acd.queue.bean.LineElement;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AcdQueue {

    // 等待队列
    protected BlockingQueue<LineElement> waitingQueue;

    // 处理中的排队映射
    protected Map<String, LineElement> prcessingMap;

    // 处理队列
    protected Queue<LineElement> processingQueue;

    // 处理失败队列
    protected Queue<LineElement> processFailedQueue;

    // 优先队列
    protected Queue<LineElement> priorityQueue;

    public AcdQueue() {
        waitingQueue = new LinkedBlockingQueue<LineElement>();
        prcessingMap = new ConcurrentHashMap<>();
        priorityQueue = new ConcurrentLinkedQueue<>();
        processFailedQueue = new ConcurrentLinkedQueue<>();
        processingQueue = new ConcurrentLinkedQueue<>();
    }

    public void add(LineElement element) {
        waitingQueue.add(element);
    }

    public void addPriority(LineElement element) {
        priorityQueue.add(element);
    }

    public void addProcessing(LineElement element) {
        prcessingMap.put(element.getId(), element);
        processingQueue.add(element);
    }

    public void removeProcessing() {
        LineElement lineElement = processingQueue.poll();
        if (null != lineElement) {
            prcessingMap.remove(lineElement.getId());
        }
    }

    public LineElement pollProcessing() {
        LineElement lineElement = processingQueue.poll();
        if (null != lineElement) {
            prcessingMap.remove(lineElement.getId());
        }
        return lineElement;
    }

    public void addProcessFailed(LineElement element) {
        processFailedQueue.add(element);
    }

    public BlockingQueue<LineElement> getWaitingQueue() {
        return waitingQueue;
    }

    public Map<String, LineElement> getProcessingMap() {
        return prcessingMap;
    }

    public Queue<LineElement> getProcessFailedQueue() {
        return processFailedQueue;
    }

    public Queue<LineElement> getPriorityQueue() {
        return priorityQueue;
    }

    @Override
    public String toString() {
        return "AcdQueue{" +
                "waitingQueue=" + waitingQueue +
                ", processingMap=" + prcessingMap +
                ", processFailedQueue=" + processFailedQueue +
                ", priorityQueue=" + priorityQueue +
                '}';
    }

    public String detail() {
        String string = "waiting : " + waitingQueue.size() + " processing : " + getProcessingMap().size() + " priority : " + priorityQueue.size() + " processFailed : " + processFailedQueue.size();
        return string;
    }
}
