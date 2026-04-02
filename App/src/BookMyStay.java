import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private int nights;
    private boolean cancelled;

    Reservation(String reservationId, String guestName,
                String roomType, String roomId, int nights) {
        this.reservationId = reservationId;
        this.guestName     = guestName;
        this.roomType      = roomType;
        this.roomId        = roomId;
        this.nights        = nights;
        this.cancelled     = false;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName()     { return guestName; }
    public String getRoomType()      { return roomType; }
    public String getRoomId()        { return roomId; }
    public int getNights()           { return nights; }
    public boolean isCancelled()     { return cancelled; }
    public void setCancelled()       { this.cancelled = true; }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2);
        roomAvailability.put("Double", 1);
        roomAvailability.put("Suite",  1);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void decrementAvailability(String roomType) {
        roomAvailability.put(roomType,
                roomAvailability.getOrDefault(roomType, 0) - 1);
    }
}

    public void incrementAvailability(String roomType) {
        roomAvailability.put(roomType,
                roomAvailability.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println("  " + entry.getKey()
                    + " : " + entry.getValue() + " room(s) available");
        }
    }
}

class CancellationService {

    private Map<String, Reservation> bookingRegistry;
    private Stack<String> rollbackStack;
    private RoomInventory inventory;

    CancellationService(RoomInventory inventory) {
        this.inventory       = inventory;
        this.bookingRegistry = new HashMap<>();
        this.rollbackStack   = new Stack<>();
    }

    public void registerBooking(Reservation reservation) {
        bookingRegistry.put(reservation.getReservationId(), reservation);
        inventory.decrementAvailability(reservation.getRoomType());
        System.out.println("Booking registered : " + reservation.getReservationId()
                + " -> " + reservation.getGuestName()
                + " (" + reservation.getRoomType() + " | "
                + reservation.getRoomId() + ")");
    }
}

    public void cancelBooking(String reservationId) {
        System.out.println("\nCancellation requested for: " + reservationId);

        if (!bookingRegistry.containsKey(reservationId)) {
            System.out.println("Cancellation FAILED: Reservation '"
                    + reservationId + "' not found.");
            return;
        }

        Reservation reservation = bookingRegistry.get(reservationId);

        if (reservation.isCancelled()) {
            System.out.println("Cancellation FAILED: Reservation '"
                    + reservationId + "' is already cancelled.");
            return;
        }

        rollbackStack.push(reservation.getRoomId());
        inventory.incrementAvailability(reservation.getRoomType());
        reservation.setCancelled();

        System.out.println("Cancellation CONFIRMED:");
        System.out.println("  Reservation ID : " + reservation.getReservationId());
        System.out.println("  Guest          : " + reservation.getGuestName());
        System.out.println("  Room Type      : " + reservation.getRoomType());
        System.out.println("  Room ID        : " + reservation.getRoomId()
                + " released back to pool");
        System.out.println("  Rollback Stack : " + rollbackStack);
    }
}

    public void displayBookingStatus() {
        System.out.println("\nBooking Registry Status:");
        for (Map.Entry<String, Reservation> entry : bookingRegistry.entrySet()) {
            Reservation r = entry.getValue();
            System.out.println("  " + r.getReservationId()
                    + " | " + r.getGuestName()
                    + " | " + r.getRoomType()
                    + " | Status: " + (r.isCancelled() ? "CANCELLED" : "CONFIRMED"));
        }
    }
}

public class BookMyStay {

    public void validate(String guestName, String roomType,
                         int nights, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidGuestNameException();
        }

        // Validate nights
        if (nights <= 0) {
            throw new InvalidNightsException(nights);
        }

        // Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidRoomTypeException(roomType);
        }

        // Validate availability
        if (!inventory.isAvailable(roomType)) {
            throw new RoomNotAvailableException(roomType);
        }
    }
}

        System.out.println("========================================");
        System.out.println(" UC10 - Booking Cancellation & Inventory Rollback ");
        System.out.println("========================================\n");

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService(inventory);

        System.out.println("Registering Confirmed Bookings:");
        cancellationService.registerBooking(
                new Reservation("RES-001", "Alice", "Single", "SIN-1", 2));
        cancellationService.registerBooking(
                new Reservation("RES-002", "Bob",   "Double", "DOU-1", 3));
        cancellationService.registerBooking(
                new Reservation("RES-003", "Carol", "Suite",  "SUI-1", 1));
        cancellationService.registerBooking(
                new Reservation("RES-004", "David", "Single", "SIN-2", 4));

        System.out.println();
        inventory.displayInventory();

        cancellationService.cancelBooking("RES-002");
        cancellationService.cancelBooking("RES-002");
        cancellationService.cancelBooking("RES-999");
        cancellationService.cancelBooking("RES-004");

        System.out.println();
        inventory.displayInventory();
        cancellationService.displayBookingStatus();

        System.out.println("\nUC10 booking cancellation and rollback completed...");
    }
}