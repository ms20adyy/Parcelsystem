public class Parcel {
    private String parcelId;
    private int weight;
    private int length;
    private int width;
    private int height;
    private boolean processed;

    // Constructor to initialize parcel details
    public Parcel(String parcelId, int weight, int length, int width, int height) {
        this.parcelId = parcelId;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.processed = false;
    }

    // Getter for parcel ID
    public String getParcelId() {
        return parcelId;
    }

    // Check if the parcel is processed
    public boolean isProcessed() {
        return processed;
    }

    // Set the processed status of the parcel
    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    // Calculate the fee based on parcel attributes
    public double calculateFee() {
        double fee = (weight * length * width * height) * 0.01;
        return Math.round(fee * 100.0) / 100.0;  
    }
}
