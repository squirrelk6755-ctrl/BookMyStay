import java.util.LinkedList;
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

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added: " + reservation.getGuestName()
                + " -> " + reservation.getRoomType()
                + " for " + reservation.getNights() + " night(s)");
    }

    public void displayQueue() {
        System.out.println("\nCurrent Booking Request Queue (FIFO Order):");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        int position = 1;
        for (Reservation r : requestQueue) {
            System.out.println(position++ + ". Guest: " + r.getGuestName()
                    + " | Room: " + r.getRoomType()
                    + " | Nights: " + r.getNights());
        }
    }
}

    public Queue<Reservation> getRequestQueue() {
        return requestQueue;
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC5 - Booking Request (First-Come-First-Served) ");
        System.out.println("========================================\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        System.out.println("Incoming Booking Requests:");
        bookingQueue.addRequest(new Reservation("Alice", "Single", 2));
        bookingQueue.addRequest(new Reservation("Bob",   "Double", 3));
        bookingQueue.addRequest(new Reservation("Carol", "Suite",  1));
        bookingQueue.addRequest(new Reservation("David", "Single", 4));

        // Display queue in arrival order
        bookingQueue.displayQueue();

        System.out.println("\nNote:");
        System.out.println("Requests are queued in FIFO order.");
        System.out.println("No inventory changes have been made at this stage.");

        System.out.println("\nUC5 booking request queue completed...");
    }
}