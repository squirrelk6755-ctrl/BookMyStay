import java.util.HashMap;

/**
 * Room class representing basic room details
 */
class Room {

    int beds;
    int size;
    double pricePerNight;

    public Room(int beds, int size, double pricePerNight) {
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/**
 * RoomInventory manages centralized availability using HashMap
 */
class RoomInventory {

    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();

        // Initialize inventory
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailableRooms(String type) {
        return availability.get(type);
    }

    public void updateAvailability(String type, int count) {
        availability.put(type, count);
    }
}

/**
 * Use Case 3: Inventory Setup
 */
public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory Status\n");

        Room single = new Room(1, 250, 1500.0);
        Room doubleRoom = new Room(2, 400, 2500.0);
        Room suite = new Room(3, 750, 5000.0);

        RoomInventory inventory = new RoomInventory();

        System.out.println("Single Room:");
        single.displayDetails();
        System.out.println("Available Rooms: " + inventory.getAvailableRooms("Single") + "\n");

        System.out.println("Double Room:");
        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + inventory.getAvailableRooms("Double") + "\n");

        System.out.println("Suite Room:");
        suite.displayDetails();
        System.out.println("Available Rooms: " + inventory.getAvailableRooms("Suite"));
    }
}