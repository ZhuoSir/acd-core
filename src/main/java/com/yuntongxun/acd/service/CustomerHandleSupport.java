package com.yuntongxun.acd.service;

import com.yuntongxun.acd.queue.CustomerQueueManager;
import com.yuntongxun.acd.queue.bean.Customer;

public class CustomerHandleSupport {

    private boolean notifySwitch = false;

    private CustomerQueueManager customerQueueManager;

    public void setCustomerQueueManager(CustomerQueueManager customerQueueManager) {
        this.customerQueueManager = customerQueueManager;
        if (notifySwitch) customerQueueManager.notifySwitchOn();
        else customerQueueManager.notifySwitchOff();
        customerQueueManager.start();
    }

    public void setNotifySwitch(boolean notifySwitch) {
        this.notifySwitch = notifySwitch;
    }

    /**
     * 排队主方法
     *
     * @param customer 排队人员参数
     *
     * */
    public void lineup(Customer customer) {
        customerQueueManager.line(customer);
    }

    /**
     * 开启排队通知
     *
     * */
    public void notifySwitchOn() {
        customerQueueManager.notifySwitchOn();
    }

    /**
     * 关闭排队通知
     *
     * */
    public void notifySwitchOff() {
        customerQueueManager.notifySwitchOff();
    }
}
