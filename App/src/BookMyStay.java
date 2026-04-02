import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private int nights;
    private double pricePerNight;

    Reservation(String reservationId, String guestName, String roomType,
                String roomId, int nights, double pricePerNight) {
        this.reservationId  = reservationId;
        this.guestName      = guestName;
        this.roomType       = roomType;
        this.roomId         = roomId;
        this.nights         = nights;
        this.pricePerNight  = pricePerNight;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName()     { return guestName; }
    public String getRoomType()      { return roomType; }
    public String getRoomId()        { return roomId; }
    public int getNights()           { return nights; }
    public double getPricePerNight() { return pricePerNight; }
    public double getTotalCost()     { return nights * pricePerNight; }
}

class BookingHistory {
    private List<Reservation> history;

    BookingHistory() {
        history = new ArrayList<>();
    }

    public void addBooking(Reservation reservation) {
        history.add(reservation);
        System.out.println("Booking recorded: " + reservation.getReservationId()
                + " -> " + reservation.getGuestName()
                + " (" + reservation.getRoomType() + ")");
    }

    public List<Reservation> getHistory() {
        return history;
    }

    public int getTotalBookings() {
        return history.size();
    }
}

class BookingReportService {

    public void displayFullHistory(BookingHistory bookingHistory) {
        List<Reservation> history = bookingHistory.getHistory();

        System.out.println("\n========================================");
        System.out.println(" Booking History Report ");
        System.out.println("========================================");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            System.out.println("\nReservation ID : " + r.getReservationId());
            System.out.println("Guest Name     : " + r.getGuestName());
            System.out.println("Room Type      : " + r.getRoomType());
            System.out.println("Room ID        : " + r.getRoomId());
            System.out.println("Nights         : " + r.getNights());
            System.out.println("Price/Night    : Rs. " + r.getPricePerNight());
            System.out.println("Total Cost     : Rs. " + r.getTotalCost());
        }
    }

    public void displaySummaryReport(BookingHistory bookingHistory) {
        List<Reservation> history = bookingHistory.getHistory();

        System.out.println("\n========================================");
        System.out.println(" Booking Summary Report ");
        System.out.println("========================================");

        int totalBookings  = history.size();
        double totalRevenue = 0;
        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : history) {
            totalRevenue += r.getTotalCost();
            roomTypeCount.put(r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("Total Bookings  : " + totalBookings);
        System.out.println("Total Revenue   : Rs. " + totalRevenue);
        System.out.println("\nBookings by Room Type:");
        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println("  " + entry.getKey() + " : " + entry.getValue() + " booking(s)");
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC8 - Booking History & Reporting ");
        System.out.println("========================================\n");

        BookingHistory bookingHistory = new BookingHistory();

        // Record confirmed bookings
        System.out.println("Recording Confirmed Bookings:");
        bookingHistory.addBooking(new Reservation("RES-001", "Alice", "Single", "SIN-1", 2, 1500.0));
        bookingHistory.addBooking(new Reservation("RES-002", "Bob",   "Double", "DOU-1", 3, 2500.0));
        bookingHistory.addBooking(new Reservation("RES-003", "Carol", "Suite",  "SUI-1", 1, 5000.0));
        bookingHistory.addBooking(new Reservation("RES-004", "David", "Single", "SIN-2", 4, 1500.0));
        bookingHistory.addBooking(new Reservation("RES-005", "Eve",   "Double", "DOU-2", 2, 2500.0));

        // Generate reports
        BookingReportService reportService = new BookingReportService();
        reportService.displayFullHistory(bookingHistory);
        reportService.displaySummaryReport(bookingHistory);

        System.out.println("\nNote:");
        System.out.println("Booking history is read-only during reporting.");
        System.out.println("\nUC8 booking history and reporting completed...");
    }
}