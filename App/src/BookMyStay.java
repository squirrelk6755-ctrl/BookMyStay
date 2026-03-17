/**
 * Book My Stay Application
 *
 * This class represents the entry point of the Hotel Booking Management System.
 * It demonstrates how a Java program starts execution using the main() method
 * and prints a welcome message to the console.
 *
 * @author Kirti
 * @version 1.0
 */
public class BookMyStay {

    /**
     * Main method – Entry point of the Java application.
     * The JVM starts execution from this method.
     *
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {

        // Application Name and Version
        String appName = "Book My Stay - Hotel Booking Management System";
        String version = "Version 1.0";

        // Welcome Message
        System.out.println("=======================================");
        System.out.println("        Welcome to " + appName);
        System.out.println("               " + version);
        System.out.println("=======================================");
        System.out.println("Application started successfully.");
        System.out.println("Thank you for using Book My Stay!");

        // Application ends
    }
}