package com.yuntongxun.acd.queue.bean;

public class Customer extends LineElement {

    private String index;

    private String account;

    public Customer(String account) {
        this.account = account;
    }

    public Customer(int index) {
        this.index = "C" + index;
    }

    public void setIndex(int index) {
        this.index = "C" + index;
    }

    @Override
    public String index() {
        return index;
    }

    public String getIndex() {
        return index;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "index=" + index() +
                ", account='" + account + '\'' +
                ", waitingCount='" + getWaitingCount() + '\'' +
                '}';
    }
}
