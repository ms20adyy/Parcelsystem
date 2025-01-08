public class ParcelModel {
   
    private QueueOfCustomers queue;
    

    private ParcelMap parcelMap;
    
    private Log log;

    // Constructor initializing the queue, parcelMap, and log
    public ParcelModel() {
        this.queue = new QueueOfCustomers();  
        this.parcelMap = new ParcelMap();      
        this.log = Log.getInstance();          
    }


    public QueueOfCustomers getQueue() {
        return queue;
    }


    public ParcelMap getParcelMap() {
        return parcelMap;
    }


    public Log getLog() {
        return log;
    }
}
