import java.util.Comparator;

class EventComparator implements Comparator<Event> {   
    @Override 
    public int compare(Event eventOne, Event eventTwo) {
        int eventOneIsEarlier = - 1;
        int eventOneIsLater = 1;

        double eventOneTime = eventOne.getTimeOfEvent();
        double eventTwoTime = eventTwo.getTimeOfEvent();
        Customer eventOneCustomer = eventOne.getCustomer();
        Customer eventTwoCustomer = eventTwo.getCustomer();

        if (eventOneTime == eventTwoTime) {
            return eventOneCustomer.getCustomerId() - eventTwoCustomer.getCustomerId();
        }
        if (eventOneTime < eventTwoTime) {
            return eventOneIsEarlier;
        } else {
            return eventOneIsLater;
        }
  

    }
}
