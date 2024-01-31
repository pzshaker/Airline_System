package controllers;

import others.HelperMethods;
import others.Identifiable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class FileController<X extends Identifiable> {
    private String filePath;
    private ArrayList<X> objects;

    public FileController(String filePath) {
        this.filePath = filePath;
        objects = getAllObjects();
    }

    public void addObjectToDB(X obj) {
        boolean found = false;
        for (X object : objects) {
            if (object.getId().equals(obj.getId())) {
                found = true;
                break;
            }
        }
        if (!found) {
            objects.add(obj);
            updateFile(objects);
        }
    }

    public void updateObjectInDB(X obj) {
        boolean found = false;
        for (X object : objects) {
            if (object.getId().equals(obj.getId())) {
                found = true;
                objects.remove(object);
                objects.add(obj);
                updateFile(objects);
                break;
            }
        }
        if (!found) {

        }
    }

    public X getObjectFromDB(String id) {
        if (Files.exists(Path.of(filePath))) {
            objects = getAllObjects();
            for (X obj : objects) {
                if (obj.getId().equals(id)) {
                    return obj;
                }
            }
        }
        return null;
    }

    public ArrayList<X> getAllObjects() {
        objects = new ArrayList<>();
        if (Files.exists(Path.of(filePath))) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                objects = (ArrayList<X>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error reading from file: " + e.getMessage());
            }
        }
        return objects;
    }

    public X removeObjectFromDB(String id) {
        X object = null;
        objects = getAllObjects();
        if (HelperMethods.isExist(id, filePath)) {
            object = getObjectFromDB(id);
            objects.remove(object);
            updateFile(objects);
        }
        return object;
    }

    private void updateFile(ArrayList<X> list) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
