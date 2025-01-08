import java.util.*;
import java.util.stream.Collectors;

public class ParcelMap {

    private Map<String, Parcel> parcels;


    public ParcelMap() {
        this.parcels = new HashMap<>();
    }

    // Method to add a Parcel to the map using its Parcel ID as the key
    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getParcelId(), parcel);
    }

    // Method to get a Parcel by its Parcel ID
    public Parcel getParcel(String parcelId) {
        return parcels.get(parcelId);
    }

    // Method to get all Parcels as a Map
    public Map<String, Parcel> getParcels() {
        return parcels;
    }

    // Method to sort the Parcels by their fee in ascending order
    public List<Parcel> sortParcelsByFee() {
        List<Parcel> parcelList = new ArrayList<>(parcels.values());
        parcelList.sort(Comparator.comparingDouble(Parcel::calculateFee));  // Sort by calculated fee
        return parcelList;
    }

    // Method to filter Parcels by their processed status (processed or not)
    public List<Parcel> filterParcelsByProcessedStatus(boolean isProcessed) {
        return parcels.values().stream()
            .filter(parcel -> parcel.isProcessed() == isProcessed)  // Filter based on processed status
            .collect(Collectors.toList());
    }
}
