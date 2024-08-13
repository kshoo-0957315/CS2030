class LeaveEvent extends Event {
    private static final int custServed = 0;
    private static final int custLeft = 1;

    public LeaveEvent(double timeOfEvent, Customer inputCustomer) {
        super(timeOfEvent,inputCustomer);
    }

    @Override
    public double getTimeOfEvent() {
        return 0.000;
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
            return String.format("%.3f %d leaves\n",this.eventTime, this.customer.getCustomerId());
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
        return 0;
    }
}
