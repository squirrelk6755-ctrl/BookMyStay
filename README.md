# Book My Stay App
### Hotel Booking Management System

---

## Overview

A console-based Java application simulating a hotel booking system, built incrementally across 12 use cases. Each use case introduces a core Java concept through a realistic booking scenario.

---

## Use Cases Summary

| UC# | Title | Key Concept | File |
|-----|-------|-------------|------|
| UC1 | Application Entry & Welcome Message | Class, main(), Console Output | `UseCase1HotelBookingApp.java` |
| UC2 | Basic Room Types & Static Availability | Abstract Class, Inheritance | `UseCase2RoomInitialization.java` |
| UC3 | Centralized Room Inventory Management | HashMap | `UseCase3InventorySetup.java` |
| UC4 | Room Search & Availability Check | Read-Only Access, Separation of Concerns | `UseCase4RoomSearch.java` |
| UC5 | Booking Request (FCFS) | Queue, FIFO | `UseCase5BookingRequestQueue.java` |
| UC6 | Reservation Confirmation & Room Allocation | Set, Uniqueness, No Double-Booking | `UseCase6RoomAllocationService.java` |
| UC7 | Add-On Service Selection | Map + List, Composition | `UseCase7AddOnServiceSelection.java` |
| UC8 | Booking History & Reporting | List, Ordered Storage | `UseCase8BookingHistoryReport.java` |
| UC9 | Error Handling & Validation | Custom Exceptions, Fail-Fast | `UseCase9ErrorHandlingValidation.java` |
| UC10 | Booking Cancellation & Rollback | Stack, LIFO | `UseCase10BookingCancellation.java` |
| UC11 | Concurrent Booking Simulation | Thread Safety, Synchronized | `UseCase11ConcurrentBookingSimulation.java` |
| UC12 | Data Persistence & Recovery | File I/O, Serialization | `UseCase12DataPersistenceRecovery.java` |
