public class Manager {
    public static void main(String[] args) {
        ParcelModel model = new ParcelModel();

        // Load customers and parcels from CSV files
        CSVLoader loader = new CSVLoader();
        loader.loadCustomers("Custs.csv", model.getQueue()); 
        loader.loadParcels("Parcels.csv", model.getParcelMap());

        // Set up the GUI for parcel management
        ParcelGUI gui = new ParcelGUI(model);
        gui.setVisible(true); 

        // Get the singleton instance of the log
        Log log = Log.getInstance();
    }
}
