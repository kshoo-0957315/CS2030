abstract class Event {
    protected final double eventTime;
    protected final Customer customer;
   
    Event(double timeOfEvent, Customer inputCustomer) {
        this.customer = inputCustomer;
        this.eventTime = timeOfEvent;
    }

    double getTimeOfEvent() {
        return this.eventTime;
    }

    Customer getCustomer() {
        return this.customer;
    }

    abstract Pair<ImList<Event>, ImList<Server>> createNextPair(ImList<Server> serversList);

    abstract String stringOutput(boolean flag);

    abstract int getCustServed();

    abstract int getCustLeft();

    abstract double getWaitTime();
}
