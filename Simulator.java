import java.util.function.Supplier;

class Simulator { 
    private final ImList<Server> serversList;
    private final ImList<Customer> customersList;
    
    private ImList<Server> initServersList(int numOfServers, int queueCapacity) {
        ImList<Server> serversList = new ImList<Server>();
        double startTime = 0.00;
        int initalQueue = 0;
        for (int i = 1; i <= numOfServers; i++) {
            serversList = serversList.add(new Server(i, startTime,
                        initalQueue, queueCapacity));
        }
        return serversList;
    }

    private ImList<Customer> initCustomersList(ImList<Double> inputArrivalTimes,
            Supplier<Double> inputServingTimes) {        
        ImList<Customer> customersList = new ImList<Customer>();
        int lastIndex = inputArrivalTimes.size();
        for (int index = 0; index < lastIndex; index++) {
            int customerId = index + 1;
            double customerArrivalTime = inputArrivalTimes.get(index);
            customersList = customersList.add(
                    new Customer(customerId, customerArrivalTime, inputServingTimes));
        }
        return customersList;
    }

    Simulator(int numOfServers, int queueCapacity, ImList<Double> inputArrivalTimes,
            Supplier<Double> inputServingTimes) {
        this.serversList = initServersList(numOfServers, queueCapacity);
        this.customersList = initCustomersList(inputArrivalTimes, inputServingTimes);
    }

    private PQ<Event> initPQ(ImList<Customer> customersList) {
        PQ<Event> pq = new PQ<Event>(new EventComparator());
        int customersListSize = customersList.size();
        for (int index = 0; index < customersListSize; index++) {
            Customer inputCustomer = customersList.get(index);
            double timeOfEvent = inputCustomer.getArrivalTime();
            ArrivalEvent arrivalEvent = new ArrivalEvent(timeOfEvent, inputCustomer);
            pq = pq.add(arrivalEvent);
        }
        return pq;
    }

    private String pqProcessing(PQ<Event> pq, ImList<Server> serversList) {
        String finalString = "";
        int custServed = 0;
        int custLeft = 0;
        int newEventIndex = 0;
        double waitTime = 0.0;
        double averageWaitTime = 0.0;
        boolean flag = true;

        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pollingOutput = pq.poll();
            Event event = pollingOutput.first();
            pq = pollingOutput.second();
            Pair<ImList<Event>, ImList<Server>> nextPair = event.createNextPair(serversList);
            ImList<Event> nextEvent = nextPair.first();
            serversList = nextPair.second();

            finalString = finalString + event.stringOutput(flag);
            custServed = custServed + event.getCustServed();
            custLeft = custLeft + event.getCustLeft();
            waitTime = waitTime + event.getWaitTime();

            if (!nextEvent.isEmpty()) {
                pq = pq.add(nextEvent.get(newEventIndex));
            }
        }
        if (custServed > 0) {
            averageWaitTime = waitTime / custServed;
        }
        finalString = finalString + String.format("[%.3f %d %d]",
                averageWaitTime, custServed,custLeft);
        return finalString;
    }

    public String simulate() {
        String finalOutput = "";
        PQ<Event> pq = initPQ(this.customersList);
        finalOutput = pqProcessing(pq, this.serversList);
        return finalOutput;
    }
}
