package com.yuntongxun.acd.queue.notification;

import com.yuntongxun.acd.queue.bean.LineElement;

import java.util.Date;

public class QueueNotification {

    private LineElement lineElement;

    private int preCount;

    private Date date;

    public QueueNotification() {
    }

    public QueueNotification(LineElement lineElement, int preCount, Date date) {
        this.lineElement = lineElement;
        this.preCount = preCount;
        this.date = date;
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

    @Override
    public String toString() {
        return "QueueNotification{" +
                "lineElement=" + lineElement +
                ", preCount=" + preCount +
                ", date=" + date +
                '}';
    }
}
