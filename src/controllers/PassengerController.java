package controllers;

import others.*;

import java.util.ArrayList;
import java.util.Scanner;


public class PassengerController {
    private ArrayList<Passenger> passengers;
    private FileController<Passenger> fileController;
    private FlightController flightController;
    private Scanner scanner;


    public PassengerController(FlightController flightController) {
        fileController = new FileController<>("users.txt");
        this.passengers = getPassengersFromDB();
        this.flightController = flightController;
    }

    public void bookFlight(Passenger passenger) {
        scanner = new Scanner(System.in);
        System.out.println("Booking Flight...");

        if (!flightController.displayFlightsForPassenger()) return;

        Flight flight = getFlight();
        int choice;
        Seat seat;
        while (true) {
            try {
                flight.getAirplane().displayAvailableSeats();
                System.out.print("\nChoose your seat number: ");
                choice = scanner.nextInt();
                if (HelperMethods.isOutOfIndex(flight.getAirplane().getSeatsCount(), choice - 1)) {
                    throw new Exception();
                }
                seat = flight.getAirplane().getSeat(choice - 1);
                if (seat.isBooked()) {
                    System.out.println("This seat already booked. Select another one.");
                    continue;
                }
                seat.setBooked(true);
                flight.assignPassenger(passenger);
                Ticket ticket = new Ticket(passenger, flight, seat);
                passenger.addTicket(ticket);
                flightController.updateFlight(flight);
                fileController.updateObjectInDB(passenger);
                System.out.println("Your Ticket:\n" + ticket);
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input. Try again.");
                scanner.nextLine();
            }
        }
    }

    public void cancelFlight(Passenger passenger) {
        scanner = new Scanner(System.in);
        System.out.println("Canceling Ticket...");
        while (true) {
            if (!passenger.displayTickets()) return;
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            if (HelperMethods.isOutOfIndex(passenger.getTicketsCount(), choice - 1)) {
                System.out.println("Invalid Input. Try again.");
                continue;
            }

            Ticket ticket = passenger.cancelTicket(choice - 1);
            Flight flight = passenger.getFlightFromTicket(ticket);
            flight.removePassenger(passenger);
            ticket.getSeat().setBooked(false);

            flightController.updateFlight(flight);
            fileController.updateObjectInDB(passenger);
            break;
        }
    }

    private Flight getFlight() {
        int choice;
        Flight flight;
        while (true) {
            try {
                System.out.print("Choice: ");
                choice = scanner.nextInt();
                if (HelperMethods.isOutOfIndex(flightController.getFlights().size(), choice - 1)) {
                    throw new Exception();
                }
                flight = flightController.getFlights().get(choice - 1);
                break;
            } catch (Exception e) {
                System.out.println("Invalid Input. Try again.");
                scanner.nextLine();
            }
        }
        return flight;
    }

    public void removePassenger() {
        scanner = new Scanner(System.in);
        fileController = new FileController<>("users.txt");
        System.out.print("Enter passenger id: ");
        String id = scanner.next();
        if (HelperMethods.isExist(id, "users.txt")) {
            passengers.remove(fileController.removeObjectFromDB(id));
            System.out.println("Passenger with id {" + id + "} has been removed successfully.");
        } else {
            System.out.println("Passenger not found.");
        }
    }

    public void addPassenger(Passenger passenger) {
        fileController.addObjectToDB(passenger);
        passengers.add(passenger);
    }

    public AccountHolder getPassengerById(String id) {
        return fileController.getObjectFromDB(id);
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    private ArrayList<Passenger> getPassengersFromDB() {
        return fileController.getAllObjects();
    }

    public void displayPassengers() {
        HelperMethods.displayList("users.txt");
    }

    public boolean isAdmin(String id) {
        Identifiable admin = fileController.getObjectFromDB(id);
        if (admin.getId().equalsIgnoreCase("admin")) {
            return true;
        }
        return false;
    }

}
