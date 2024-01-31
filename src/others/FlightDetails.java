package others;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class FlightDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Country departureLocation;
    private LocalDateTime departureTime;
    private Country arrivalLocation;
    private LocalDateTime arrivalTime;

    public FlightDetails(Country departureLocation, LocalDateTime departureTime, Country arrivalLocation, LocalDateTime arrivalTime) {
        this.departureLocation = departureLocation;
        this.departureTime = departureTime;
        this.arrivalLocation = arrivalLocation;
        this.arrivalTime = arrivalTime;
    }

    public Country getDepartureLocation() {
        return departureLocation;
    }

    public Country getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
