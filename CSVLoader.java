import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVLoader {
    
    // Method to load customer data from CSV file into the queue
    public void loadCustomers(String filePath, QueueOfCustomers queue) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String name = customerData[0];
                String customerId = customerData[1];
                Customer customer = new Customer(name, customerId);
                queue.addCustomer(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load parcel data from CSV file into the parcel map
    public void loadParcels(String filePath, ParcelMap parcelMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parcelData = line.split(",");
                String parcelId = parcelData[0];
                int weight = Integer.parseInt(parcelData[1]);
                int length = Integer.parseInt(parcelData[2]);
                int width = Integer.parseInt(parcelData[3]);
                int height = Integer.parseInt(parcelData[4]);
                Parcel parcel = new Parcel(parcelId, weight, length, width, height);
                parcelMap.addParcel(parcel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
