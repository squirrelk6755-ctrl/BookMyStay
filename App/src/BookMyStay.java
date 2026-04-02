import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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

class SharedInventory {
    private Map<String, Integer> roomAvailability;
    private Map<String, Integer> roomCounter;

    SharedInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 3);
        roomAvailability.put("Double", 2);
        roomAvailability.put("Suite",  1);

        roomCounter = new HashMap<>();
        roomCounter.put("Single", 0);
        roomCounter.put("Double", 0);
        roomCounter.put("Suite",  0);
    }

    public synchronized boolean allocateRoom(Reservation reservation) {
        String roomType = reservation.getRoomType();

        if (!roomAvailability.containsKey(roomType)) {
            System.out.println("[" + Thread.currentThread().getName() + "] "
                    + "FAILED - Invalid room type: " + roomType);
            return false;
        }

        if (roomAvailability.get(roomType) <= 0) {
            System.out.println("[" + Thread.currentThread().getName() + "] "
                    + "FAILED - No " + roomType + " rooms available for "
                    + reservation.getGuestName());
            return false;
        }

        // Allocate room
        int count = roomCounter.get(roomType) + 1;
        roomCounter.put(roomType, count);
        String roomId = roomType.substring(0, 3).toUpperCase() + "-" + count;
        roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);

        System.out.println("[" + Thread.currentThread().getName() + "] "
                + "CONFIRMED - Guest: " + reservation.getGuestName()
                + " | Room: " + roomType
                + " | Room ID: " + roomId
                + " | Nights: " + reservation.getNights()
                + " | Remaining: " + roomAvailability.get(roomType));
        return true;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println("  " + entry.getKey()
                    + " : " + entry.getValue() + " room(s) remaining");
        }
    }
}

class SharedBookingQueue {
    private Queue<Reservation> queue;

    SharedBookingQueue() {
        queue = new LinkedList<>();
    }

    public synchronized void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public synchronized Reservation pollRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingThread extends Thread {
    private SharedBookingQueue bookingQueue;
    private SharedInventory inventory;

    BookingThread(String name, SharedBookingQueue bookingQueue,
                  SharedInventory inventory) {
        super(name);
        this.bookingQueue = bookingQueue;
        this.inventory    = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation = bookingQueue.pollRequest();
            if (reservation == null) break;

            inventory.allocateRoom(reservation);

            try {
                Thread.sleep(50); // Simulate processing delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("========================================");
        System.out.println(" UC11 - Concurrent Booking Simulation ");
        System.out.println("========================================\n");

        SharedInventory    inventory    = new SharedInventory();
        SharedBookingQueue bookingQueue = new SharedBookingQueue();

        // Add booking requests to shared queue
        bookingQueue.addRequest(new Reservation("Alice",   "Single", 2));
        bookingQueue.addRequest(new Reservation("Bob",     "Double", 3));
        bookingQueue.addRequest(new Reservation("Carol",   "Suite",  1));
        bookingQueue.addRequest(new Reservation("David",   "Single", 2));
        bookingQueue.addRequest(new Reservation("Eve",     "Double", 1));
        bookingQueue.addRequest(new Reservation("Frank",   "Single", 3));
        bookingQueue.addRequest(new Reservation("Grace",   "Suite",  2));
        bookingQueue.addRequest(new Reservation("Henry",   "Single", 1));

        System.out.println("Launching concurrent booking threads...\n");

        // Create and start booking threads
        BookingThread t1 = new BookingThread("Thread-1", bookingQueue, inventory);
        BookingThread t2 = new BookingThread("Thread-2", bookingQueue, inventory);
        BookingThread t3 = new BookingThread("Thread-3", bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to complete
        t1.join();
        t2.join();
        t3.join();

        // Display final inventory
        inventory.displayInventory();

        System.out.println("\nNote:");
        System.out.println("Synchronized methods ensured no double allocation.");
        System.out.println("\nUC11 concurrent booking simulation completed...");
    }
}