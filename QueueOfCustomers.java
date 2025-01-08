import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCustomers {
    private Queue<Customer> customers;

    public QueueOfCustomers() {
        this.customers = new LinkedList<>();
    }

    // Adds a customer to the queue
    public void addCustomer(Customer customer) {
        customers.add(customer);  
    }

    // Removes the customer at the front of the queue
    public void removeCustomer() {
        customers.poll();  
    }

    // Getter method to retrieve the queue
    public Queue<Customer> getQueue() {
        return customers;  
    }

    // Returns the number of customers in the queue
    public int size() {
        return customers.size();  
    }
}
