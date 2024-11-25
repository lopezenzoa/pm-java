package model;

import model.enums.Visibility;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID ID;
    private String name;
    private String email;
    private String password;
    private Visibility visibility;

    public User(String name, String email, String password) {
        this.ID = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.visibility = Visibility.VISIBLE;
    }

    /**
     * Construye un usuario a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa al usuario.
     * @author Enzo.
     * */
    /*
    public Usuario(JSONObject jsonObject) {
        try {
            this.ID = jsonObject.getInt("id");
            this.name = jsonObject.getString("nombre");
            this.email = jsonObject.getString("apellido");
            this.email = jsonObject.getString("email");
            this.password = jsonObject.getString("titulo");
            this.password = jsonObject.getInt("password");

            String altaOBajaJSON = jsonObject.getString("estado");
            this.altaObaja = AltaBaja.valueOf(altaOBajaJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

     */

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

    /***
     * Serializa la clase usuario.
     * @return un objeto de tipo JSONObject con los atributos del usuario.
     */
    /*
    public JSONObject serializar() {
        JSONObject usuarioJson = null;

        try {
            usuarioJson = new JSONObject();
            usuarioJson.put("id", id);
            usuarioJson.put("nombre", nombre);
            usuarioJson.put("apellido", apellido);
            usuarioJson.put("email", email);
            usuarioJson.put("titulo", titulo);
            usuarioJson.put("estado", altaObaja.toString());
            usuarioJson.put("password", password);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return usuarioJson;
    }

     */

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
        return "User{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", visibility=" + visibility;
    }
}
