package model.serializers;

import model.User;
import model.enums.Visibility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public abstract class UserSerializer implements Serializable<User> {
    /**
     * Creates a new user using as a base a JSONObject.
     * @param userJSON is the JSONObject used as starting point.
     * */
    @Override
    public User deserialize(JSONObject userJSON) {
        User user = new User();

        try {
            user.setID(UUID.fromString(userJSON.getString("ID")));
            user.setName(userJSON.getString("name"));
            user.setEmail(userJSON.getString("email"));
            user.setPassword(userJSON.getString("password"));
            user.setVisibility(Visibility.valueOf(userJSON.getString("visibility")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Serializes the class User.
     * @return a JSONObject representation of the class.
     * */
    @Override
    public JSONObject serialize(User user) {
        JSONObject userJSON = null;

        try {
            userJSON = new JSONObject();

            userJSON.put("ID", user.getID());
            userJSON.put("name", user.getName());
            userJSON.put("email", user.getEmail());
            userJSON.put("password", user.getPassword());
            userJSON.put("visibility", user.getVisibility());

        } catch (JSONException e){
            e.printStackTrace();
        }

        return userJSON;
    }
}
