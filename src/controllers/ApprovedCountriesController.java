package controllers;

import others.Country;
import others.HelperMethods;

import java.util.ArrayList;
import java.util.Scanner;

public class ApprovedCountriesController {
    private ArrayList<Country> approvedCountries;
    private FileController<Country> fileController;

    public ApprovedCountriesController() {
        fileController = new FileController<>("countries.txt");
        this.approvedCountries = getApprovedCountriesFromDB();
    }

    public void addApprovedCountry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding Approved Country...");
        System.out.print("Enter country's name: ");
        String inputCountry = scanner.nextLine();
        Country country = new Country(inputCountry.toLowerCase());
        addCountryInDB(country);
    }

    public void showApprovedCountries() {
        int i = 1;
        for (Country ctr : approvedCountries) {
            System.out.println(i + ". " + HelperMethods.capitalize(ctr.getName()));
            i++;
        }
    }

    public void showApprovedCountriesExcept(Country country) {
        int i = 1;
        for (Country ctr : approvedCountries) {
            if (!ctr.getId().equalsIgnoreCase(country.getId())) {
                System.out.println(i + ". " + HelperMethods.capitalize(ctr.getName()));
            }
            i++;
        }
    }

    public ArrayList<Country> getApprovedCountries() {
        return approvedCountries;
    }

    private void addCountryInDB(Country country) {

        if (!isExist(country)) {
            fileController.addObjectToDB(country);
            approvedCountries = getApprovedCountriesFromDB();
            System.out.println(country + " has been approved successfully!");
        } else {
            System.out.println("This country already approved.");
        }
    }

    public void removeApprovedCountry() {
        Scanner scanner = new Scanner(System.in);
        if (!approvedCountries.isEmpty()) {
            System.out.println("Removing Approved Country...");
            showApprovedCountries();
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            if (choice - 1 < getApprovedCountries().size()) {
                removeCountryFromDB(getApprovedCountries().get(choice - 1).getId());
            } else {
                System.out.println("Invalid Input. Please enter number in range!");
            }
        } else {
            System.out.println("Nothing is here!");
        }
    }

    private void removeCountryFromDB(String id) {
        Country country;
        if (HelperMethods.isExist(id, "countries.txt")) {
            country = fileController.removeObjectFromDB(id);
            approvedCountries = getApprovedCountriesFromDB();
            System.out.println(HelperMethods.capitalize(country.getName()) + " has been removed successfully.");
        } else {
            System.out.println("Country not found!");
        }
    }

    public ArrayList<Country> getApprovedCountriesFromDB() {
        return fileController.getAllObjects();
    }

    public void displayApprovedCountries() {
        HelperMethods.displayList("countries.txt");
    }

    private boolean isExist(Country country) {
        for (Country ctry : approvedCountries) {
            if (ctry.getId().equalsIgnoreCase(country.getId())) {
                return true;
            }
        }
        return false;
    }
}
