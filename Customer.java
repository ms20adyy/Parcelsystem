public class Customer {
    private String name;
    private String id;

    // Constructor to initialize customer with name and Parcel id
    public Customer(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Getter for customer's name
    public String getName() {
        return name;
    }

    // Getter for parcel id
    public String getId() {
        return id;
    }
}
