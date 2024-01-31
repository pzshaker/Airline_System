package others;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Passenger implements Serializable, AccountHolder {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private String id;
    private String email;
    private String password;
    private ArrayList<Ticket> tickets;

    public Passenger(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.tickets = new ArrayList<>();
        generateID();
        Airline.incrementPassengerCount();
    }

    private void generateID() {
        // The ID is formed from the first two letters of the passenger's name, their age, and the current passenger count.
        this.id = (name.substring(0, 2) + age + Airline.getPassengerCount()).toLowerCase();
    }

    public String getName() {
        return HelperMethods.capitalize(name);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean displayTickets() {
        // Displays all tickets associated with the passenger.
        if (tickets.isEmpty()) {
            System.out.println("No Tickets Found");
            return false;
        }
        int counter = 1;
        for (Ticket ticket : tickets) {
            System.out.println(counter + ":\n" + ticket);
        }
        return true;
    }

    public Flight getFlightFromTicket(Ticket ticket) {
        return ticket.getFlight();
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public Ticket cancelTicket(int index) {
        return this.tickets.remove(index);
    }

    public int getTicketsCount() {
        return this.tickets.size();
    }

    @Override
    public String toString() {
        return HelperMethods.capitalize(this.name) + ", id: "+  this.id;
    }
}
