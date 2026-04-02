import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType  = roomType;
        this.nights    = nights;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType()  { return roomType; }
    public int getNights()       { return nights; }
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

    public boolean isAvailable(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0) > 0;
    }

    public void decrementAvailability(String roomType) {
        roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);
    }
}

class RoomAllocationService {

    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomTypeAllocations;
    private Map<String, Integer> roomTypeCounter;

    RoomAllocationService() {
        allocatedRoomIds     = new HashSet<>();
        roomTypeAllocations  = new HashMap<>();
        roomTypeCounter      = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();

        if (!inventory.isAvailable(roomType)) {
            System.out.println("Booking FAILED for " + reservation.getGuestName()
                    + " - No " + roomType + " rooms available.");
            return;
        }

        // Generate unique room ID
        int count = roomTypeCounter.getOrDefault(roomType, 0) + 1;
        roomTypeCounter.put(roomType, count);
        String roomId = roomType.toUpperCase().substring(0, 3) + "-" + count;

        // Ensure room ID is unique
        if (allocatedRoomIds.contains(roomId)) {
            System.out.println("Booking FAILED for " + reservation.getGuestName()
                    + " - Room ID conflict: " + roomId);
            return;
        }

        // Record allocation
        allocatedRoomIds.add(roomId);
        roomTypeAllocations.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

        // Update inventory immediately
        inventory.decrementAvailability(roomType);

        System.out.println("Booking CONFIRMED:");
        System.out.println("  Guest     : " + reservation.getGuestName());
        System.out.println("  Room Type : " + roomType);
        System.out.println("  Room ID   : " + roomId);
        System.out.println("  Nights    : " + reservation.getNights());
        System.out.println("  Remaining : " + inventory.getRoomAvailability().get(roomType)
                + " " + roomType + " room(s) left");
        System.out.println();
    }

    public void displayAllocations() {
        System.out.println("Allocated Room Summary:");
        for (Map.Entry<String, Set<String>> entry : roomTypeAllocations.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC6 - Reservation Confirmation & Room Allocation ");
        System.out.println("========================================\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Build booking request queue (FIFO)
        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.offer(new Reservation("Alice", "Single", 2));
        requestQueue.offer(new Reservation("Bob",   "Double", 3));
        requestQueue.offer(new Reservation("Carol", "Suite",  1));
        requestQueue.offer(new Reservation("David", "Single", 4));
        requestQueue.offer(new Reservation("Eve",   "Suite",  2));

        // Process requests
        RoomAllocationService allocationService = new RoomAllocationService();

        System.out.println("Processing Booking Requests (FIFO):\n");
        while (!requestQueue.isEmpty()) {
            Reservation reservation = requestQueue.poll();
            allocationService.allocateRoom(reservation, inventory);
        }

        // Display final allocation summary
        allocationService.displayAllocations();

        System.out.println("\nUC6 room allocation completed...");
    }
}