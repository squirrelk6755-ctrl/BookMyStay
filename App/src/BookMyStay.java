import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Service {
    private String serviceName;
    private double cost;

    Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost        = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost()        { return cost; }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
        System.out.println("Service added for " + reservationId
                + " -> " + service.getServiceName()
                + " (Rs. " + service.getCost() + ")");
    }

    public void displayServices(String reservationId) {
        List<Service> services = reservationServices.get(reservationId);

        System.out.println("\nAdd-On Services for Reservation: " + reservationId);
        if (services == null || services.isEmpty()) {
            System.out.println("  No services selected.");
            return;
        }

        double totalCost = 0;
        for (Service s : services) {
            System.out.println("  - " + s.getServiceName()
                    + " : Rs. " + s.getCost());
            totalCost += s.getCost();
        }
        System.out.println("  Total Add-On Cost : Rs. " + totalCost);
    }
}

    public void displayAllReservationServices() {
        System.out.println("\nAll Reservation Add-On Summaries:");
        for (Map.Entry<String, List<Service>> entry : reservationServices.entrySet()) {
            String reservationId = entry.getKey();
            List<Service> services = entry.getValue();
            double total = 0;
            for (Service s : services) total += s.getCost();
            System.out.println("  " + reservationId
                    + " -> " + services.size()
                    + " service(s), Total: Rs. " + total);
        }
    }
}

public class BookMyStay {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC7 - Add-On Service Selection ");
        System.out.println("========================================\n");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Guest Alice selects add-on services for reservation RES-001
        System.out.println("Processing Add-Ons for RES-001 (Alice):");
        serviceManager.addService("RES-001", new Service("Breakfast",       350.0));
        serviceManager.addService("RES-001", new Service("Airport Pickup",  800.0));
        serviceManager.addService("RES-001", new Service("Spa Package",    1500.0));

        // Guest Bob selects add-on services for reservation RES-002
        System.out.println("\nProcessing Add-Ons for RES-002 (Bob):");
        serviceManager.addService("RES-002", new Service("Breakfast",      350.0));
        serviceManager.addService("RES-002", new Service("Late Checkout",  500.0));

        // Guest Carol selects no add-on services for reservation RES-003
        System.out.println("\nProcessing Add-Ons for RES-003 (Carol):");
        System.out.println("No add-on services selected.");

        // Display services per reservation
        serviceManager.displayServices("RES-001");
        serviceManager.displayServices("RES-002");
        serviceManager.displayServices("RES-003");

        // Display overall summary
        serviceManager.displayAllReservationServices();

        System.out.println("\nNote:");
        System.out.println("Core booking and inventory state remain unchanged.");
        System.out.println("\nUC7 add-on service selection completed...");
    }
}