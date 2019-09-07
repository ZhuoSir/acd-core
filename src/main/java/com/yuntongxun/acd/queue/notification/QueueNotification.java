package com.yuntongxun.acd.queue.notification;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.LineElement;

import java.util.Date;

public class QueueNotification {

    private LineElement lineElement;
    private int preCount;
    private Date date;

    // 排队状态，0 排队中 1 排队结束, 已分配
    private int lineStatus;

    private Agent distributedAgent;

    public QueueNotification() {
    }

    public QueueNotification(LineElement lineElement, int preCount, Date date, int lineStatus) {
        this.lineElement = lineElement;
        this.preCount = preCount;
        this.date = date;
        this.lineStatus = lineStatus;
    }

    public int getPreCount() {
        return preCount;
    }

    public void setPreCount(int preCount) {
        this.preCount = preCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public LineElement getLineElement() {
        return lineElement;
    }

    public void setLineElement(LineElement lineElement) {
        this.lineElement = lineElement;
    }

    public int getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(int lineStatus) {
        this.lineStatus = lineStatus;
    }

    public Agent getDistributedAgent() {
        return distributedAgent;
    }

    public void setDistributedAgent(Agent distributedAgent) {
        this.distributedAgent = distributedAgent;
    }

    @Override
    public String toString() {
        return "QueueNotification{" +
                "lineElement=" + lineElement +
                ", preCount=" + preCount +
                ", date=" + date +
                ", lineStatus=" + lineStatus +
                ", distributedAgent=" + distributedAgent +
                '}';
    }
}
