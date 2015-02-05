package object.user;

import java.io.Serializable;

class User implements Serializable {
    private String username;

    User(String username) {
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}