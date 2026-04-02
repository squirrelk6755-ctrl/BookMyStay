import java.io.*;
import java.util.*;

public class BookMyStay {

    static final String INVENTORY_FILE = "inventory.txt";
    static final String BOOKINGS_FILE  = "bookings.txt";

    // Save inventory to file
    static void saveInventory(Map<String, Integer> inventory) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE));
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue());
            writer.newLine();
        }
        writer.close();
        System.out.println("Inventory saved to " + INVENTORY_FILE);
    }

    // Save bookings to file
    static void saveBookings(List<String> bookings) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE));
        for (String booking : bookings) {
            writer.write(booking);
            writer.newLine();
        }
        writer.close();
        System.out.println("Bookings saved to " + BOOKINGS_FILE);
    }

    // Load inventory from file
    static Map<String, Integer> loadInventory() {
        Map<String, Integer> inventory = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                inventory.put(parts[0], Integer.parseInt(parts[1]));
            }
            reader.close();
            System.out.println("Inventory restored from " + INVENTORY_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("No inventory file found. Starting with default inventory.");
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite",  2);
        } catch (IOException e) {
            System.out.println("Error reading inventory file: " + e.getMessage());
        }
        return inventory;
    }

    // Load bookings from file
    static List<String> loadBookings() {
        List<String> bookings = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(line);
            }
            reader.close();
            System.out.println("Bookings restored from " + BOOKINGS_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("No bookings file found. Starting with empty booking history.");
        } catch (IOException e) {
            System.out.println("Error reading bookings file: " + e.getMessage());
        }
        return bookings;
    }

    static void displayInventory(Map<String, Integer> inventory) {
        System.out.println("\nCurrent Inventory:");
        inventory.forEach((k, v) -> System.out.println("  " + k + " : " + v + " room(s)"));
    }

    static void displayBookings(List<String> bookings) {
        System.out.println("\nBooking History:");
        if (bookings.isEmpty()) {
            System.out.println("  No bookings found.");
            return;
        }
        bookings.forEach(b -> System.out.println("  " + b));
    }

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC12 - Data Persistence & System Recovery ");
        System.out.println("========================================\n");

        // --- PHASE 1: Simulate system before shutdown ---
        System.out.println("--- Phase 1: Saving System State ---\n");

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 3);
        inventory.put("Double", 1);
        inventory.put("Suite",  0);

        List<String> bookings = new ArrayList<>();
        bookings.add("RES-001 | Alice | Single | SIN-1 | 2 nights");
        bookings.add("RES-002 | Bob   | Double | DOU-1 | 3 nights");
        bookings.add("RES-003 | Carol | Single | SIN-2 | 1 night");

        displayInventory(inventory);
        displayBookings(bookings);

        try {
            saveInventory(inventory);
            saveBookings(bookings);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }

        // --- PHASE 2: Simulate system restart & recovery ---
        System.out.println("\n--- Phase 2: Simulating System Restart ---\n");
        System.out.println("System restarting...");
        System.out.println("Loading persisted state...\n");

        Map<String, Integer> recoveredInventory = loadInventory();
        List<String> recoveredBookings          = loadBookings();

        displayInventory(recoveredInventory);
        displayBookings(recoveredBookings);

        System.out.println("\nSystem successfully recovered.");
        System.out.println("\nUC12 data persistence and recovery completed...");
    }
}