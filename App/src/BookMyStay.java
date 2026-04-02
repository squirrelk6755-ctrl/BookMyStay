import java.util.HashMap;
import java.util.Map;

class Room {
    private String type;
    private int beds;
    private double size;
    private double pricePerNight;

    Room(String type, int beds, double size, double pricePerNight) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }

    public String getType()          { return type; }
    public int getBeds()             { return beds; }
    public double getSize()          { return size; }
    public double getPricePerNight() { return pricePerNight; }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite",  2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }
}

class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            System.out.println("Beds: "            + singleRoom.getBeds());
            System.out.println("Size: "            + singleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + singleRoom.getPricePerNight());
            System.out.println("Available: "       + availability.get("Single"));
            System.out.println();
        }

        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            System.out.println("Beds: "            + doubleRoom.getBeds());
            System.out.println("Size: "            + doubleRoom.getSize() + " sqft");
            System.out.println("Price per night: " + doubleRoom.getPricePerNight());
            System.out.println("Available: "       + availability.get("Double"));
            System.out.println();
        }

        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            System.out.println("Beds: "            + suiteRoom.getBeds());
            System.out.println("Size: "            + suiteRoom.getSize() + " sqft");
            System.out.println("Price per night: " + suiteRoom.getPricePerNight());
            System.out.println("Available: "       + availability.get("Suite"));
            System.out.println();
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room singleRoom = new Room("Single", 1, 250, 1500.0);
        Room doubleRoom = new Room("Double", 2, 400, 2500.0);
        Room suiteRoom  = new Room("Suite",  3, 750, 5000.0);

        RoomSearchService searchService = new RoomSearchService();

        System.out.println("Room Search\n");
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }
}