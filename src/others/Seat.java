package others;

import enums.SeatClass;

import java.io.Serial;
import java.io.Serializable;

public class Seat implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int seatsCount = 0;
    private String id;
    private SeatClass classType;
    private boolean isBooked;
    private double price;

    public Seat(SeatClass classType) {
        this.classType = classType;
        this.isBooked = false;
        seatsCount++;
        setPrice(classType);
        generateID();
    }

    public boolean isBooked() {
        return this.isBooked;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    private void generateID() {
        // The ID is formed from the first three letters of the seat's class type name and the current seat count.
        this.id = (classType.name().substring(0, 3) + seatsCount).toLowerCase();
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(SeatClass classType) {
        // Sets the price of the seat based on its class type.
        switch (classType) {
            case ECONOMY -> this.price = 100;
            case BUSINESS -> this.price = 200;
            case FIRST_CLASS -> this.price = 300;
        }
    }

    @Override
    public String toString() {
        return HelperMethods.capitalize(classType.name().toLowerCase());
    }

    @Override
    public String getId() {
        return this.id;
    }
}
