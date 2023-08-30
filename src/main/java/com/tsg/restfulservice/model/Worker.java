package com.tsg.restfulservice.model;

public class Worker {
    private int workerId;
    private String workerFirstName;
    private String workerLastName;
    private String workerEmail;

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getWorkerFirstName() {
        return workerFirstName;
    }

    public void setWorkerFirstName(String workerFirstName) {
        this.workerFirstName = workerFirstName;
    }

    public String getWorkerLastName() {
        return workerLastName;
    }

    public void setWorkerLastName(String workerLastName) {
        this.workerLastName = workerLastName;
    }

    public String getWorkerEmail() {
        return workerEmail;
    }

    public void setWorkerEmail(String workerEmail) {
        this.workerEmail = workerEmail;
    }
}
