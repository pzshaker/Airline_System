package others;

import java.io.Serial;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Ticket implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Passenger passenger;
    private Flight flight;
    private Seat seat;

    public Ticket(Passenger passenger, Flight flight, Seat seat) {
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public Seat getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Name: " + passenger.getName() +
                "\nFrom: " + flight.getFlightDetails().getDepartureLocation() +
                "\nTo: " + flight.getFlightDetails().getArrivalLocation() +
                "\nDeparture Time: " + flight.getFlightDetails().getDepartureTime().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm")) +
                "\nDeparture Time: " + flight.getFlightDetails().getArrivalTime().format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm")) +
                "\nSeat: " + seat.getId() +
                "\nClass Type: " + seat +
                "\nTotal Price: " + seat.getPrice();
    }
}
