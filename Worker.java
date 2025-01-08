public class Worker {
    private QueueOfCustomers queue;  
    private ParcelMap parcelMap;  
    private Log log;  

   
    public Worker(QueueOfCustomers queue, ParcelMap parcelMap) {
        this.queue = queue;
        this.parcelMap = parcelMap;
        this.log = Log.getInstance();  
    }

    // Method to process the next customer from the queue
    public void processCustomer() {
        if (!queue.getQueue().isEmpty()) {
            // Remove the first customer from the queue
            Customer customer = queue.getQueue().poll();
            
            log.logEvent("Customer joined queue: " + customer.getName() + " (" + customer.getId() + ")");
            
            
            Parcel parcel = parcelMap.getParcel(customer.getId());
            
            // If a parcel is found and it is not processed yet, process it
            if (parcel != null && !parcel.isProcessed()) {
                parcel.setProcessed(true);  

                log.logEvent("Parcel " + parcel.getParcelId() + " processed for customer " + customer.getName());
            } else {
             
                log.logEvent("No parcel found or already processed for customer " + customer.getName());
            }

            // Log event of customer being removed from the queue after processing
            log.logEvent("Customer removed from queue: " + customer.getName() + " (" + customer.getId() + ")");
            
           
            log.writeLogToFile("events_log.txt");
        }
    }
}
