class WaitEvent extends Event {
    private static final int custServed = 0;
    private static final int custLeft = 0;
    private final int serverIndex;
    private final int queueNum;
    private final boolean reWait;
   
    public WaitEvent(double timeOfEvent, Customer inputCustomer, int inputServerIndex,
            int qNum, boolean inputReWait) {
        super(timeOfEvent, inputCustomer);
        this.serverIndex = inputServerIndex;
        this.queueNum = qNum;
        this.reWait = inputReWait;
    }

    double getTimeOfEvent() {
        return this.eventTime;
    }

    Customer getCustomer() {
        return this.customer;
    }

    int getQueueNum() {
        return this.queueNum;
    }

    boolean isReWait() {
        return this.reWait;
    }

    private Server updateServer(Server server) {
        int newQueue = server.getQueue();
        if (!this.reWait) {
            newQueue = newQueue + 1;
        }
        return new Server(server.getServerId(), server.getNextAvailableTime(),
                newQueue, server.getQueueCapacity());
    }

    @Override
    public Pair<ImList<Event>, ImList<Server>> createNextPair(ImList<Server> serversList) {
        ImList<Event> eventList = new ImList<Event>();
        Server updatedServer = updateServer(serversList.get(this.serverIndex));
        double nextServingTime = updatedServer.getNextAvailableTime();
        if (this.queueNum <= 1) {
            eventList = eventList.add(
                    new ServeEvent(nextServingTime, this.customer, this.serverIndex));
        } else {
            boolean isReWaitEvent = true;
            eventList = eventList.add(new WaitEvent(nextServingTime, this.customer,
                        this.serverIndex, this.queueNum - 1, isReWaitEvent));
        }
        ImList<Server> newServersList = serversList.set(this.serverIndex, updatedServer);
        Pair<ImList<Event>, ImList<Server>> nextPair = 
            new Pair<ImList<Event>, ImList<Server>>(eventList, newServersList);
        return nextPair;
    }

    @Override
    public String stringOutput(boolean flag) {
        if (flag != this.reWait) {
            int serverId = this.serverIndex + 1;
            return String.format("%.3f %d waits at %d\n",
                this.eventTime, this.customer.getCustomerId(), serverId);
        } else {
            return "";
        }
    }

    @Override
    public int getCustServed() {
        return this.custServed;
    }

    @Override
    public int getCustLeft() {
        return this.custLeft;
    }

    @Override
    public double getWaitTime() {
        return 0.0;
    }
}
