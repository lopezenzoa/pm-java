package model;

import model.enums.Visibility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID ID;
    private String name;
    private String email;
    private String password;
    private Visibility visibility;

    public User() {}

    public User(UUID ID, String name, String email, String password, Visibility visibility) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.visibility = visibility;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(ID, user.ID) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && visibility == user.visibility;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, email, password, visibility);
    }

    @Override
    public String toString() {
        return String.format(
                "User\n" +
                        "  ID: %s,\n" +
                        "  Name: '%s',\n" +
                        "  Email: '%s',\n" +
                        "  Password: '%s',\n" +
                        "  Visibility: %s\n"
                ,
                ID,
                name,
                email,
                password,
                visibility
        );
    }
}
