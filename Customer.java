import java.util.function.Supplier;

class Customer {
    private final double arrivalTime;
    private final Supplier<Double> servingTime;
    private final int id; 

    Customer(int customerId, double customerArrivalTime, Supplier<Double> customerServingTime) {
        this.arrivalTime = customerArrivalTime;
        this.servingTime = customerServingTime;
        this.id = customerId;
    }

    int getCustomerId() {
        return this.id;
    }

    double getArrivalTime() {
        return this.arrivalTime;
    }

    double getServingTime() {
        return this.servingTime.get(); 
    }
}
