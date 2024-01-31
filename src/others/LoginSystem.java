package others;

import controllers.PassengerController;

import java.util.Scanner;

public class LoginSystem {
    Airline airline;
    private PassengerController passengerController;

    public LoginSystem(Airline airline) {
        this.airline = airline;
        passengerController = this.airline.getPassengerController();
    }

    public String startPage() {
        // Displays the start page of the login system.
        // Prompts the user to identify as either Passenger or Admin and returns their choice.
        Scanner scanner = new Scanner(System.in);
        String userType;
        System.out.println("*****************************************");
        System.out.println("***** Welcome to the Java Airlines! *****");
        System.out.println("*****************************************");
        System.out.println("Are you Passenger or Admin? (P / A)");
        do {
            System.out.print("Choice: ");
            userType = scanner.next().toLowerCase();
        } while (!userType.equals("p") && !userType.equals("a"));

        return userType;
    }

    public AccountHolder signup() {
        // Handles the signup process for new passengers.
        Scanner scanner = new Scanner(System.in);
        System.out.println("*****Sign Up*****");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        while (isEmailRegistered(email)) {
            System.out.println("This email already registered! use another one.");
            System.out.print("Email: ");
            email = scanner.nextLine();
        }
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Passenger passenger = new Passenger(name, age, email, password);
        passengerController.addPassenger(passenger);
        System.out.println("Welcome " + HelperMethods.capitalize(name) + " your account has been created successfully!");
        System.out.println("Your ID: {" + passenger.getId() + "} you will need it for the login process.");
        return passenger;
    }

    public AccountHolder signIn(String userType) {
        // Handles the sign-in process for users.
        Scanner scanner = new Scanner(System.in);
        String inputId;
        String inputPass;
        AccountHolder user = null;
        System.out.println("*****Sign In*****");

        while (true) {
            System.out.print("ID: ");
            inputId = scanner.nextLine();

            if (userType.equalsIgnoreCase("p") && inputId.equals("admin")) {
                System.out.println("Admin access is not allowed. Try again or '0' to exit.");
                continue;
            }

            if (userType.equalsIgnoreCase("a") && !airline.getPassengerController().isAdmin(inputId)) {
                System.out.println("Passenger access is not allowed. Try again or '0' to exit.");
                continue;
            }

            if (inputId.equals("0")) System.exit(0);
            else if (inputId.equals("1")) {
                signup();
                break;
            }

            user = passengerController.getPassengerById(inputId);


            if (user == null) {
                System.out.println("This id {" + inputId + "} does not exist in our database! Try again. '0' to exit / '1' to signup. ");
                continue;
            }

            while (true) {
                System.out.print("Password: ");
                inputPass = scanner.nextLine();
                if (user.getPassword().equals(inputPass)) {
                    System.out.println("Access Granted!");
                    break;
                } else {
                    System.out.println("Invalid Password.");
                }
            }
            break;
        }

        return user;
    }

    private boolean isEmailRegistered(String email) {
        // Checks if the given email is already registered in the system.
        for (AccountHolder user : passengerController.getPassengers()) {
            if (user instanceof Passenger && ((Passenger) user).getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
