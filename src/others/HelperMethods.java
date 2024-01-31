package others;

import controllers.FileController;

import java.util.ArrayList;

public class HelperMethods {
    private static FileController<Identifiable> fileController;

    public static String capitalize(String text) {
        // Capitalizes the first letter of each word in a given string.
        String[] words = text.split(" ");
        text = "";
        for (String word : words) {
            text += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
        }
        return text.trim();
    }

    public static boolean isExist(String id, String path) {
        // Checks if an object with a given ID exists in the specified file.
        fileController = new FileController<>(path);
        for (Identifiable obj : fileController.getAllObjects()) {
            if (obj.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static void displayList(String path) {
        // Displays a list of objects from a specified file.
        int counter = 1;
        fileController = new FileController<>(path);
        ArrayList<Identifiable> objects = fileController.getAllObjects();
        // if there is no users and one admin there, it will display nothing.
        if (objects.isEmpty() || objects.size() == 1 && objects.get(0) instanceof Admin) {
            System.out.println("Nothing is Here!");
        } else {
            for (Identifiable item : fileController.getAllObjects()) {
                // making sure that any admin in the users.txt file won't get printed.
                if (path.equalsIgnoreCase("users.txt")) {
                    if (!item.getId().equalsIgnoreCase("admin")) {
                        System.out.println(counter + ". " + item);
                        counter++;
                    }
                } else {
                    System.out.println(counter + ". " + item);
                    counter++;
                }
            }
        }
    }

    public static boolean isOutOfIndex(int maxIndex, int index) {
        // Checks if a given index is out of bounds in a list with a specified maximum index.
        if (index >= maxIndex || index < 0) {
            System.out.println("Invalid Input!");
            return true;
        }
        return false;
    }

}
