package controllers;

import others.Airplane;
import others.HelperMethods;

import java.util.ArrayList;
import java.util.Scanner;

public class AirplaneController {
    private ArrayList<Airplane> airplanes;
    private FileController<Airplane> fileController;

    public AirplaneController() {
        this.fileController = new FileController<>("airplanes.txt");
        this.airplanes = getAirplanesFromDB();
    }

    private ArrayList<Airplane> getAirplanesFromDB() {
        return fileController.getAllObjects();
    }


    public void buyAirplane() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Buying Airplane...");
        System.out.print("Enter airplane name: ");
        String airplaneName = scanner.nextLine();
        Airplane airplane = new Airplane(airplaneName);
        fileController.addObjectToDB(airplane);
        System.out.println("Airplane with id {" + airplane.getId() + "} has been bought successfully!");
    }

    public void sellAirplane() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selling Airplane...");
        System.out.print("Enter airplane id: ");
        String id = scanner.nextLine();
        if (HelperMethods.isExist(id, "airplanes.txt")) {
            fileController.removeObjectFromDB(id);
            System.out.println("Airplane with id {" + id + "} has been sold successfully!");
        } else {
            System.out.println("Airplane not found.");
        }
    }

    public void updateAirplane(Airplane airplane) {
        fileController.updateObjectInDB(airplane);
    }

    public ArrayList<Airplane> getAirplanes() {
        return airplanes;
    }

    public boolean showAvailableAirplanes() {
        boolean availableFound = false;
        int i = 1;
        for (Airplane airplane : airplanes) {
            if (airplane.isAvailable()) {
                System.out.println(i + ". " + HelperMethods.capitalize(airplane.getName()));
                i++;
                availableFound = true;
            }
        }
        if (!availableFound) {
            System.out.println("No available airplanes at the moment.");
        }
        return availableFound;
    }

    public void displayAirplanes() {
        HelperMethods.displayList("airplanes.txt");
    }
}
