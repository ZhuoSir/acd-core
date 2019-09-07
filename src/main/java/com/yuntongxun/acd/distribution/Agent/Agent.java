package com.yuntongxun.acd.distribution.Agent;

public class Agent {

    public static final int FREE = 0;
    public static final int BUSY = 1;
    public static final int DENY = 2;

    // 0 未分配，1 已分配 2 拒绝分配
    private int status = FREE;

    private String agentId;

    private String account;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public Agent(int index) {
        this.agentId = "A" + index;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "status=" + status +
                ", agentId='" + agentId + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
