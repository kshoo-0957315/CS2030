class ArrivalEvent extends Event {
    private static final int custServed = 0;
    private static final int custLeft = 0;
    private static final int noAvailableServers = - 1;

    public ArrivalEvent(double timeOfEvent, Customer inputCustomer) {
        super(timeOfEvent, inputCustomer);
    }

    private int findAvailableServer(ImList<Server> serversList, double time) {
        int index = 0;
        while (index < serversList.size() && (serversList.get(index).canServe(time) == false)) {
            index = index + 1;
        }
        if (index < serversList.size()) {
            return index;
        } else {
            index = 0;
            while (index < serversList.size() && 
                    (serversList.get(index).canQueue() == false)) {
                index = index + 1;
            }
        }
        if (index < serversList.size()) {
            return index;
        } else {
            return noAvailableServers;
        }
    }

    @Override
    public Pair<ImList<Event>, ImList<Server>> createNextPair(ImList<Server> serversList) {
        ImList<Event> eventList = new ImList<Event>();
        int serverIndex = findAvailableServer(serversList, this.eventTime);
        if (serverIndex == noAvailableServers) {
            eventList = eventList.add(new LeaveEvent(this.eventTime, this.customer));
        } else {
            if (serversList.get(serverIndex).canServe(this.eventTime)) {
                eventList = eventList.add(new ServeEvent(this.eventTime,
                            this.customer, serverIndex));
            } else {
                int queueNum = serversList.get(serverIndex).getQueue() + 1;
                boolean isReWaitEvent = false;
                eventList = eventList.add(new WaitEvent(this.eventTime, this.customer, serverIndex,
                            queueNum, isReWaitEvent));
            }
        }
        Pair<ImList<Event>, ImList<Server>> nextPair = 
            new Pair<ImList<Event>, ImList<Server>>(eventList, serversList);
        return nextPair;
    }

    @Override
    public String stringOutput(boolean flag) {
        if (flag) {
            return String.format(
                    "%.3f %d arrives\n", this.eventTime, this.customer.getCustomerId());
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

