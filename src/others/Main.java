package others;

import controllers.FileController;

import java.util.Scanner;

public class Main {
    static Airline airline;
    static LoginSystem loginSystem;

    public static void main(String[] args) throws InterruptedException {
        airline = new Airline();
        loginSystem = new LoginSystem(airline);
        loginPage();

    }

    public static void loginPage() throws InterruptedException {
        String userType = loginSystem.startPage();
        if (userType.equals("p")) {
            passengerPage();
        } else {
            adminPage();
        }
    }

    private static void adminPage() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin", "admin");
        FileController<Admin> fileController = new FileController<>("users.txt");
        fileController.addObjectToDB(admin);
        String userID = loginSystem.signIn("a").getId();

//        checks if it's an Admin
        if (airline.getPassengerController().isAdmin(userID)) {
            String choice;
            do {
                adminMenu();
                System.out.print("Choice: ");
                choice = scanner.next();
                switch (choice) {
                    case "1":
                        airline.getAirplaneController().buyAirplane();
                        break;
                    case "2":
                        airline.getAirplaneController().sellAirplane();
                        break;
                    case "3":
                        airline.getApprovedCountriesController().addApprovedCountry();
                        break;
                    case "4":
                        airline.getApprovedCountriesController().removeApprovedCountry();
                        break;
                    case "5":
                        airline.getFlightController().addFlight();
                        break;
                    case "6":
                        airline.getFlightController().removeFlight();
                        break;
                    case "7":
                        airline.getPassengerController().removePassenger();
                        break;
                    case "8":
                        airline.getPassengerController().displayPassengers();
                        break;
                    case "9":
                        airline.getFlightController().displayFlightsForAdmin();
                        break;
                    case "10":
                        airline.getAirplaneController().displayAirplanes();
                        break;
                    case "11":
                        airline.getApprovedCountriesController().displayApprovedCountries();
                        break;
                }
                System.out.println("==============================");
                Thread.sleep(1000);
            } while (!choice.equals("0"));
        }
    }

    private static void passengerPage() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String input;
        Passenger passenger;
        System.out.println("Do you have an account? (Y / N)");
        do {
            System.out.print("Choice: ");
            input = scanner.next().toLowerCase();
        } while (!input.equals("y") && !input.equals("n"));

        if (input.equals("y")) {
            passenger = (Passenger) loginSystem.signIn("p");
        } else {
            passenger = (Passenger) loginSystem.signup();
        }

        String choice;
        do {
            passengerMenu();
            System.out.print("Choice: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    airline.getFlightController().displayFlightsForPassenger();
                    break;
                case "2":
                    airline.getPassengerController().bookFlight(passenger);
                    break;
                case "3":
                    airline.getPassengerController().cancelFlight(passenger);
                    break;
                case "4":
                    passenger.displayTickets();
                    break;
            }
            System.out.println("==============================");
            Thread.sleep(1000);
        } while (!choice.equalsIgnoreCase("0"));
        System.out.println("Thank you for using Java Airlines!");
    }

    private static void passengerMenu() {
        System.out.println("Passenger Menu:");
        System.out.println("1. Show Available Flights");
        System.out.println("2. Book Ticket");
        System.out.println("3. Cancel Ticket");
        System.out.println("4. Display My Tickets");
        System.out.println("0. Exit");
    }

    public static void adminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Buy Airplane");
        System.out.println("2. Sell Airplane");
        System.out.println("3. Add Approved Country");
        System.out.println("4. Remove Approved Country");
        System.out.println("5. Add Flight");
        System.out.println("6. Remove Flight");
        System.out.println("7. Remove Passenger");
        System.out.println("8. Display All Passengers");
        System.out.println("9. Display All Flights");
        System.out.println("10. Display All Airplanes");
        System.out.println("11. Display All Approved Countries");
        System.out.println("0. Exit");
    }
}
