import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ParcelGUI extends JFrame {
    private ParcelModel model;
    private JTextArea logArea;
    private JList<String> parcelList;
    private JList<String> customerQueueList;
    private JButton processButton;
    private JButton sortButton;
    private JButton filterButton;
    private JButton addCustomerButton;
    private JCheckBox filterCheckBox;
    private JButton removeCustomerButton;
    private JButton addParcelButton;
    
    // Constructor to set up GUI components
    public ParcelGUI(ParcelModel model) {
        this.model = model;
        setTitle("Parcel Processing System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel to display parcel list
        JPanel parcelPanel = new JPanel();
        parcelPanel.setLayout(new BorderLayout());
        parcelList = new JList<>();
        parcelPanel.add(new JScrollPane(parcelList), BorderLayout.CENTER);

        // Button to sort parcels by fee
        sortButton = new JButton("Sort Parcels by Fee");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Parcel> sortedParcels = model.getParcelMap().sortParcelsByFee();
                updateParcelList(sortedParcels);
            }
        });
        
        // Button to remove customer
        removeCustomerButton = new JButton("Remove Customer");
        removeCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });

        // Button to add a new parcel
        addParcelButton = new JButton("Add Parcel");
        addParcelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParcel();
            }
        });

        // Button to filter processed parcels
        filterButton = new JButton("Filter Processed Parcels");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean filterProcessed = filterCheckBox.isSelected();
                List<Parcel> filteredParcels = model.getParcelMap().filterParcelsByProcessedStatus(filterProcessed);
                updateParcelList(filteredParcels);
            }
        });

        filterCheckBox = new JCheckBox("Show Processed Parcels");

        // List to display customer queue
        customerQueueList = new JList<>();
        updateCustomerQueueList();

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.add(sortButton);
        controlPanel.add(filterButton);
        controlPanel.add(filterCheckBox);
        controlPanel.add(removeCustomerButton);
        controlPanel.add(addParcelButton);

        // Button to add a new customer
        addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        controlPanel.add(addCustomerButton);

        // Button to process the next customer
        processButton = new JButton("Process Next Customer");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Worker worker = new Worker(model.getQueue(), model.getParcelMap());
                worker.processCustomer();
                updateLog();
                updateCustomerQueueList();
            }
        });
        
        add(parcelPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(new JScrollPane(customerQueueList), BorderLayout.WEST);

        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        add(logScrollPane, BorderLayout.SOUTH);
        add(processButton, BorderLayout.EAST);

        updateParcelList(model.getParcelMap().getParcels().values().stream().collect(Collectors.toList()));
    }


    private void addCustomer() {
        JTextField nameField = new JTextField(15);
        JTextField idField = new JTextField(15);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Parcel ID:"));
        panel.add(idField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Enter Customer Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String id = idField.getText();
            if (!name.isEmpty() && !id.isEmpty()) {
                Customer newCustomer = new Customer(name, id);
                model.getQueue().addCustomer(newCustomer);
                Log.getInstance().logEvent("Customer added to queue: " + name + " (" + id + ")");
                JOptionPane.showMessageDialog(this, "Customer Added: " + name + " (" + id + ")");
                updateCustomerQueueList();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both name and ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void removeCustomer() {
        JTextField searchField = new JTextField(15);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(new JLabel("Enter Name or Parcel ID:"));
        panel.add(searchField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Enter Customer Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String searchValue = searchField.getText().trim();

            if (!searchValue.isEmpty()) {
                Customer removedCustomer = null;
                for (Customer customer : model.getQueue().getQueue()) {
                    if (customer.getId().equals(searchValue) || customer.getName().equalsIgnoreCase(searchValue)) {
                        removedCustomer = customer;
                        break;
                    }
                }

                if (removedCustomer != null) {
                    model.getQueue().getQueue().remove(removedCustomer);
                    model.getLog().logEvent("Customer removed from queue: " + removedCustomer.getName() + " (" + removedCustomer.getId() + ")");
                    updateCustomerQueueList();
                    updateLog();
                    JOptionPane.showMessageDialog(this, "Customer removed: " + removedCustomer.getName() + " (" + removedCustomer.getId() + ")");
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found in the queue.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid customer ID or name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateParcelList(List<Parcel> parcels) {
        DefaultListModel<String> parcelListModel = new DefaultListModel<>();
        for (Parcel parcel : parcels) {
            String status = parcel.isProcessed() ? "Processed" : "Pending";
            parcelListModel.addElement(parcel.getParcelId() + " - Fee: " + parcel.calculateFee() + " - " + status);
        }
        parcelList.setModel(parcelListModel);
    }


    private void updateCustomerQueueList() {
        DefaultListModel<String> customerQueueModel = new DefaultListModel<>();
        for (Customer customer : model.getQueue().getQueue()) {
            customerQueueModel.addElement(customer.getName() + " (" + customer.getId() + ")");
        }
        customerQueueList.setModel(customerQueueModel);
    }
    

    private void addParcel() {
        JTextField parcelIdField = new JTextField(15);
        JTextField weightField = new JTextField(15);
        JTextField lengthField = new JTextField(15);
        JTextField widthField = new JTextField(15);
        JTextField heightField = new JTextField(15);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Parcel ID:"));
        panel.add(parcelIdField);
        panel.add(new JLabel("Weight:"));
        panel.add(weightField);
        panel.add(new JLabel("Length:"));
        panel.add(lengthField);
        panel.add(new JLabel("Width:"));
        panel.add(widthField);
        panel.add(new JLabel("Height:"));
        panel.add(heightField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Enter Parcel Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String parcelId = parcelIdField.getText().trim();
            String weightText = weightField.getText().trim();
            String lengthText = lengthField.getText().trim();
            String widthText = widthField.getText().trim();
            String heightText = heightField.getText().trim();

            if (!parcelId.isEmpty() && !weightText.isEmpty() && !lengthText.isEmpty() && !widthText.isEmpty() && !heightText.isEmpty()) {
                try {
                    int weight = Integer.parseInt(weightText);
                    int length = Integer.parseInt(lengthText);
                    int width = Integer.parseInt(widthText);
                    int height = Integer.parseInt(heightText);

                    Parcel newParcel = new Parcel(parcelId, weight, length, width, height);
                    model.getParcelMap().addParcel(newParcel);
                    model.getLog().logEvent("Parcel added: " + parcelId + " (Weight: " + weight + ", Dimensions: " + length + "x" + width + "x" + height + ")");
                    updateParcelList(model.getParcelMap().getParcels().values().stream().collect(Collectors.toList()));

                    JOptionPane.showMessageDialog(this, "Parcel added successfully: " + parcelId);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numerical values for weight and dimensions.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateLog() {
        logArea.setText(model.getLog().getLog());
    }
}
