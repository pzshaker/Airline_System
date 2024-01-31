package others;

import controllers.*;

import java.io.*;

public class Airline implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int passengersCount;
    private AirplaneController airplaneController;
    private ApprovedCountriesController approvedCountriesController;
    private FlightController flightController;
    private PassengerController passengerController;


    public Airline() {
        this.airplaneController = new AirplaneController();
        this.approvedCountriesController = new ApprovedCountriesController();
        this.flightController = new FlightController(approvedCountriesController, airplaneController);
        this.passengerController = new PassengerController(flightController);
        passengersCount = passengerController.getPassengers().size();
    }

    public static void incrementPassengerCount() {
        passengersCount++;
    }

    public static int getPassengerCount() {
        return passengersCount;
    }

    public AirplaneController getAirplaneController() {
        return airplaneController;
    }

    public ApprovedCountriesController getApprovedCountriesController() {
        return approvedCountriesController;
    }

    public FlightController getFlightController() {
        return flightController;
    }

    public PassengerController getPassengerController() {
        return passengerController;
    }
}