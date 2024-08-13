class DoneEvent extends Event {
    private static final int custServed = 1;
    private static final int custLeft = 0;
    private final Server server;
    
    public DoneEvent(double timeOfEvent, Customer inputCustomer, Server inputServer) {
        super(timeOfEvent, inputCustomer);
        this.server = inputServer;
    }

    @Override
    public Pair<ImList<Event>, ImList<Server>> createNextPair(ImList<Server> serversList) {
        ImList<Event> eventsList = new ImList<Event>();
        Pair<ImList<Event>, ImList<Server>> nextPair = 
            new Pair<ImList<Event>, ImList<Server>>(eventsList, serversList);
        return nextPair;
    }

    @Override
    public String stringOutput(boolean flag) {
        if (flag) {
            return String.format("%.3f %d done serving by %d\n", this.eventTime,
                this.customer.getCustomerId(), this.server.getServerId());
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
