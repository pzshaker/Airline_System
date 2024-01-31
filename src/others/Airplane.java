package others;

import controllers.FileController;
import enums.AirplaneStatus;
import enums.SeatClass;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Airplane implements Serializable, Identifiable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String id;
    private ArrayList<Seat> seats;
    private AirplaneStatus status;

    public Airplane(String name) {
        this.name = name;
        this.status = AirplaneStatus.AVAILABLE;
        this.seats = new ArrayList<>();
        generateID();
        fillSeats();
        FileController<Airplane> fileController = new FileController<>("airplanes.txt");
        fileController.addObjectToDB(this);
    }

    public boolean isAvailable() {
        return this.status == AirplaneStatus.AVAILABLE;
    }

    private void generateID() {
        // The ID is a combination of the first two letters of the airplane's name and a random number.
        this.id = (this.name.substring(0, 2).toLowerCase() + new Random().nextInt(100)).toLowerCase();
    }

    public int getSeatsCount() {
        return seats.size();
    }

    private void fillSeats() {
        // Populates the airplane with seats, divided into different classes:
        // Economy, Business, and First Class.
        for (int i = 0; i < 64; i++) {
            if (i < 32) seats.add(new Seat(SeatClass.ECONOMY));
            else if (i < 52) seats.add(new Seat(SeatClass.BUSINESS));
            else seats.add(new Seat(SeatClass.FIRST_CLASS));
        }
    }

    public boolean isFull() {
//        Checks if the airplane is fully booked, returns true if at least one seat available.
        for (Seat seat : seats) {
            if (!seat.isBooked()) return false;
        }
        return false;
    }

    public void setStatus(AirplaneStatus status) {
        this.status = status;
    }

    public void displayAvailableSeats() {
        int totalSeatsInRow = 0;
        for (int i = 0; i < seats.size(); i++) {
            if (totalSeatsInRow % 4 == 0) {
                System.out.println();
                if (i == 0) {
                    System.out.println("** ECONOMY CLASS **");
                } else if (i == 32) {
                    System.out.println("** BUSINESS CLASS **");
                } else if (i == 52) {
                    System.out.println("** FIRST CLASS **");
                }
                totalSeatsInRow = 0;
            }

            if (!seats.get(i).isBooked()) {
                if (totalSeatsInRow == 0) System.out.print("   ");
                System.out.printf("%02d ", (i + 1));
                totalSeatsInRow++;
            } else {
                if (totalSeatsInRow == 0) System.out.print("   ");
                System.out.print("-- ");
                totalSeatsInRow++;
            }
            if (totalSeatsInRow == 2) {
                System.out.print("| ");
            }
        }
    }

    public Seat getSeat(int index) {
        return seats.get(index);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return HelperMethods.capitalize(this.name) + ", id: " + this.id;
    }
}
