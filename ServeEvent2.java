class ServeEvent extends Event {
    private static final int custServed = 0;
    private static final int custLeft = 0;
    private final int serverIndex;

    public ServeEvent(double timeOfEvent, Customer inputCustomer, int inputServerIndex) {
        super(timeOfEvent,inputCustomer);
        this.serverIndex = inputServerIndex;
    }

    private Server updateServer(Server server) {
        double nextAvailableTime = this.eventTime + this.customer.getServingTime();
        int newQueue = server.getQueue();
        if (newQueue > 0) {
            newQueue = newQueue - 1;
        }
        return new Server(server.getServerId(), nextAvailableTime, 
                newQueue, server.getQueueCapacity());
    }
    
    @Override
    public Pair<ImList<Event>, ImList<Server>> createNextPair(ImList<Server> serversList) {
        ImList<Event> eventsList = new ImList<Event>();
        Server updatedServer = updateServer(serversList.get(serverIndex));
        double newEventTime = updatedServer.getNextAvailableTime();

        eventsList = eventsList.add(new DoneEvent(newEventTime, this.customer, updatedServer));
        ImList<Server> newServersList = serversList.set(this.serverIndex, updatedServer);
        Pair<ImList<Event>, ImList<Server>> nextPair = 
            new Pair<ImList<Event>, ImList<Server>>(eventsList, newServersList);
        return nextPair;
    }
       
    @Override
    public String stringOutput(boolean flag) {
        if (flag) {
            int serverId = serverIndex + 1;
            return String.format("%.3f %d serves by %d\n", this.eventTime,
                this.customer.getCustomerId(), serverId);
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
        return this.eventTime - this.customer.getArrivalTime();
    }
}

