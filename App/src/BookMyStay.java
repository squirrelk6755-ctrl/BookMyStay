import java.util.HashMap;
import java.util.Map;

// Custom exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

// Custom exception for invalid room type
class InvalidRoomTypeException extends InvalidBookingException {
    InvalidRoomTypeException(String roomType) {
        super("Invalid room type specified: '" + roomType
                + "'. Valid types are: Single, Double, Suite.");
    }
}

// Custom exception for no availability
class RoomNotAvailableException extends InvalidBookingException {
    RoomNotAvailableException(String roomType) {
        super("No rooms available for type: '" + roomType + "'.");
    }
}

// Custom exception for invalid nights
class InvalidNightsException extends InvalidBookingException {
    InvalidNightsException(int nights) {
        super("Invalid number of nights: " + nights
                + ". Nights must be greater than zero.");
    }
}

// Custom exception for empty guest name
class InvalidGuestNameException extends InvalidBookingException {
    InvalidGuestNameException() {
        super("Guest name cannot be null or empty.");
    }
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

    public boolean isValidRoomType(String roomType) {
        return roomAvailability.containsKey(roomType);
    }

    public boolean isAvailable(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0) > 0;
    }

    public void decrementAvailability(String roomType) {
        roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);
    }
}

class InvalidBookingValidator {

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

class ValidatedBookingService {

    private InvalidBookingValidator validator;
    private RoomInventory inventory;
    private int bookingCounter;

    ValidatedBookingService(RoomInventory inventory) {
        this.inventory      = inventory;
        this.validator      = new InvalidBookingValidator();
        this.bookingCounter = 0;
    }

    public void processBooking(String guestName, String roomType, int nights) {
        System.out.println("\nProcessing booking for: " + guestName
                + " | Room: " + roomType + " | Nights: " + nights);
        try {
            validator.validate(guestName, roomType, nights, inventory);

            bookingCounter++;
            String reservationId = "RES-00" + bookingCounter;
            inventory.decrementAvailability(roomType);

            System.out.println("Booking CONFIRMED:");
            System.out.println("  Reservation ID : " + reservationId);
            System.out.println("  Guest          : " + guestName);
            System.out.println("  Room Type      : " + roomType);
            System.out.println("  Nights         : " + nights);
            System.out.println("  Remaining      : "
                    + inventory.getRoomAvailability().get(roomType)
                    + " " + roomType + " room(s) left");

        } catch (InvalidBookingException e) {
            System.out.println("Booking FAILED: " + e.getMessage());
        }
    }
}

public class BookMyStay{

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC9 - Error Handling & Validation ");
        System.out.println("========================================");

        RoomInventory inventory = new RoomInventory();
        ValidatedBookingService bookingService = new ValidatedBookingService(inventory);

        // Valid booking
        bookingService.processBooking("Alice", "Single", 2);

        // Invalid room type
        bookingService.processBooking("Bob", "Penthouse", 3);

        // Invalid nights
        bookingService.processBooking("Carol", "Double", 0);

        // Empty guest name
        bookingService.processBooking("", "Suite", 1);

        // Valid booking
        bookingService.processBooking("David", "Double", 2);

        // Valid booking - exhaust Single rooms
        bookingService.processBooking("Eve", "Single", 1);

        // Room not available
        bookingService.processBooking("Frank", "Single", 1);

        System.out.println("\n========================================");
        System.out.println(" Final Inventory State ");
        System.out.println("========================================");
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + entry.getKey() + " : " + entry.getValue() + " room(s) remaining");
        }

        System.out.println("\nUC9 error handling and validation completed...");
    }
}