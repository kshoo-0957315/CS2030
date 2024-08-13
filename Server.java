class Server {
    private final double nextAvailableTime;
    private final int id;
    private final int queue;
    private final int queueCapacity;

    Server(int serverId, double availableTime, int inputQueue, int inputQueueCapacity) {
        this.id = serverId;
        this.nextAvailableTime = availableTime;
        this.queue = inputQueue;
        this.queueCapacity = inputQueueCapacity;
    }

    int getServerId() {
        return this.id;
    }

    double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    boolean canServe(double customerArrivalTime) {
        return customerArrivalTime >= this.nextAvailableTime;
    }

    int getQueueCapacity() {
        return this.queueCapacity;
    }

    int getQueue() {
        return this.queue;
    }

    boolean canQueue() {
        return this.queue < this.queueCapacity;
    }
}
