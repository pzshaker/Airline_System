package others;

import java.io.Serial;
import java.io.Serializable;

public class Admin implements Serializable, AccountHolder {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String password;

    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
