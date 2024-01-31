package others;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;


// This class represents the country that is approved for the airline's departure and arrival.
public class Country implements Serializable, Identifiable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String id;

    public Country(String name) {
        this.name = name;
        this.id = generateID();
    }

    private String generateID() {
        // The ID is a combination of the first two letters of the country's name and a random number.
        return (name.substring(0, 2) + new Random().nextInt(100)).toLowerCase();
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return HelperMethods.capitalize(this.name);
    }
}
