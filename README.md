# Parcel Management System

## Overview
The **Parcel Management System** is a robust application designed to streamline the management of parcels and customers. The system follows the **Model-View-Controller (MVC)** architectural pattern, ensuring modularity, scalability, and a clean separation of concerns.

## Features
- **Add New Parcels:** Users can add parcels with attributes like `parcelId`, `weight`, `size`, and `status`.
- **Add Customers:** A user-friendly interface allows adding customer information, which integrates seamlessly into the parcel queue system.
- **Update Parcel Status:** Change and track the processing state of parcels.
- **Customer Queue Management:** Ensures first-come, first-served handling of parcels.
- **Generate Reports:** Logs customer activities and parcel transactions (basic functionality included; future enhancements planned).
- **Sort and Filter Parcels:** Advanced filtering and sorting based on processing status or charges.

## Implementation
### MVC Pattern
- **Model:** Handles the core business logic and data structures.
  - `ParcelModel`: Manages parcels, logs, and customer queues.
  - Classes like `Parcel`, `Customer`, `QueueOfCustomers`, and `ParcelMap` support data management and business operations.
- **View:** Manages the user interface.
  - `ParcelGUI`: Provides interactive components for managing parcels and customers.
- **Controller:** Orchestrates interactions between the Model and View.
  - `Manager`: Initializes the application and coordinates components.
  - `Worker`: Handles parcel and queue processing.

### Key Classes
- **Parcel Class:** Manages parcel attributes and operations (`calculateFee`, `setProcessed`).
- **Customer Class:** Handles customer data and integrates with the queue.
- **ParcelMap Class:** Facilitates parcel sorting and filtering.
- **Log Class:** Tracks significant system activities for reporting.


