package controllers;

import enums.AirplaneStatus;
import others.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightController {
    private FileController<Flight> fileController;
    private ArrayList<Flight> flights;
    private ApprovedCountriesController approvedCountriesController;
    private AirplaneController airplaneController;
    private Scanner scanner;

    public FlightController(ApprovedCountriesController approvedCountriesController, AirplaneController airplaneController) {
        scanner = new Scanner(System.in);
        this.fileController = new FileController<>("flights.txt");
        this.flights = getFlightsFromDB();
        this.approvedCountriesController = approvedCountriesController;
        this.airplaneController = airplaneController;
    }

    public void updateFlight(Flight flight) {
        fileController.updateObjectInDB(flight);
    }

    public void removeFlight() {
        System.out.println("Removing Flight...");
        System.out.print("Flight ID: ");
        String id = scanner.next();
        if (HelperMethods.isExist(id, "flights.txt")) {
            Flight flight = fileController.removeObjectFromDB(id);
            Airplane airplane = flight.getAirplane();
            airplane.setStatus(AirplaneStatus.AVAILABLE);
            airplaneController.updateAirplane(airplane);
            System.out.println("Flight with id {" + id + "} has been removed successfully.");
        } else {
            System.out.println("Flight not found.");
        }
    }


    private ArrayList<Flight> getFlightsFromDB() {
        return fileController.getAllObjects();
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void displayFlightsForAdmin() {
        HelperMethods.displayList("flights.txt");
    }

    public boolean displayFlightsForPassenger() {
        boolean found = false;
        if (flights.isEmpty()) {
            System.out.println("There are no Flights Available.");
            return found;
        }
        int counter = 1;
        for (Flight flight : flights) {
            if (flight.isAvailable()) {
                System.out.println(
                        counter + ":" +
                                "\nDeparture: " + flight.getFlightDetails().getDepartureLocation() +
                                "\nTime: " + flight.getFlightDetails().getDepartureTime().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm")) +
                                "\nArrival: " + flight.getFlightDetails().getArrivalLocation() +
                                "\nTime: " + flight.getFlightDetails().getArrivalTime().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm")));
                counter++;
                found = true;
            }
        }
        return found;
    }

    public void addFlight() {
        Airplane airplane = getAirplane();
        if (airplane == null) return;

        FlightDetails flightDetails = getFlightDetails();
        if (flightDetails == null) {
            System.out.println("There are no flights at the moment.");
            return;
        }

        airplane.setStatus(AirplaneStatus.IN_FLIGHT);
        airplaneController.updateAirplane(airplane);
        Flight flight = new Flight(airplane, flightDetails);
        fileController.addObjectToDB(flight);
        System.out.println("Flight with id {" + flight.getId() + "} has been added.");
    }

    private Airplane getAirplane() {
        System.out.println("Adding Flight...");
        System.out.println("Available airplanes: ");
        boolean availableFound = airplaneController.showAvailableAirplanes();

        if (!availableFound) return null;

        Airplane airplane;
        int airplaneChoice;

        while (true) {
            try {
                System.out.print("Choice: ");
                airplaneChoice = scanner.nextInt();
                airplane = airplaneController.getAirplanes().get(airplaneChoice - 1);
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input. Try again.");
                scanner.nextLine();
            }
        }
        return airplane;
    }

    private FlightDetails getFlightDetails() {
        ArrayList<Country> countries = approvedCountriesController.getApprovedCountries();
        if (countries.isEmpty()) return null;

        int departureChoice;
        int arrivalChoice;
        while (true) {
            departureChoice = selectCountry("Departure Location:");
            if (!HelperMethods.isOutOfIndex(countries.size(), departureChoice - 1)) break;
        }
        while (true) {
            arrivalChoice = selectCountry("Arrival Location:", countries.get(departureChoice - 1));
            if (!HelperMethods.isOutOfIndex(countries.size(), arrivalChoice - 1)) break;
        }

        return getFlightDetails(countries, departureChoice, arrivalChoice - 1);
    }

    private FlightDetails getFlightDetails(ArrayList<Country> countries, int departureChoice, int arrivalChoice) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        LocalDateTime departureDateTime;
        LocalDateTime arrivalDateTime;
        while (true) {
            try {
                System.out.print("Departure time (yyyy-mm-dd-hh-mm): ");
                departureDateTime = LocalDateTime.parse(scanner.next(), formatter);
                break;
            } catch (Exception e) {
                System.out.println("Invalid format. Try again.");
            }
        }

        while (true) {
            try {
                System.out.print("Arrival time (yyyy-mm-dd-hh-mm): ");
                arrivalDateTime = LocalDateTime.parse(scanner.next(), formatter);
                break;
            } catch (Exception e) {
                System.out.println("Invalid format. Try again.");
            }
        }
        return new FlightDetails(countries.get(departureChoice - 1), departureDateTime, countries.get(arrivalChoice), arrivalDateTime);
    }

    private int selectCountry(String prompt) {
        System.out.println(prompt);
        approvedCountriesController.showApprovedCountries();
        System.out.print("Choice: ");
        return scanner.nextInt();
    }

    private int selectCountry(String prompt, Country excludeCountry) {
        System.out.println(prompt);
        approvedCountriesController.showApprovedCountriesExcept(excludeCountry);
        System.out.print("Choice: ");
        return scanner.nextInt();
    }
}
