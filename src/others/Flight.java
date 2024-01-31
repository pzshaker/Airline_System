package others;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Flight implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private Airplane airplane;
    private ArrayList<Passenger> passengers;
    private FlightDetails flightDetails;

    public Flight(Airplane airplane, FlightDetails flightDetails) {
        this.airplane = airplane;
        this.flightDetails = flightDetails;
        this.id = generateID();
        this.passengers = new ArrayList<>();
    }

    public void assignPassenger(Passenger passenger) {
        // Adds a passenger to the flight.
        passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        // Removes a passenger from the flight.
        this.passengers.remove(passenger);
    }

    public boolean isAvailable() {
        // Checks if there are available seats on the airplane, returns true if the airplane is not full.
        if (airplane.isFull()) return false;
        return true;
    }

    public Airplane getAirplane() {
        return airplane;
    }


    private String generateID() {
        /*
         The ID is composed of a prefix "JA", the airplane's ID, and the first two letters of both the departure and
         arrival locations' names.
         JA: Java Airline
         */
        return "JA" + airplane.getId() + flightDetails.getDepartureLocation().getName().substring(0, 2) + flightDetails.getArrivalLocation().getName().substring(0, 2);
    }

    @Override
    public String getId() {
        return id;
    }

    public String toString() {
        return "Departure: " + flightDetails.getDepartureLocation() + ", Arrival: " + flightDetails.getArrivalLocation() +
                ", Airplane: " + airplane.getName() + ", id: " + this.id;
    }

    public FlightDetails getFlightDetails() {
        return flightDetails;
    }
}
