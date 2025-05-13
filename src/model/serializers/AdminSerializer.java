package model.serializers;

import model.Admin;
import model.Leader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public class AdminSerializer extends UserSerializer {
    LeaderSerializer serializer;

    public AdminSerializer() {
        this.serializer = new LeaderSerializer();
    }

    /**
     * Creates a new admin using as a base a JSONObject.
     * @param adminJSON is the JSONObject used as starting point.
     * */
    public Admin deserialize(JSONObject adminJSON) {
        HashSet<Leader> dependants = new HashSet<>();

        try {
            // builds the basic info for the admin
            Admin admin = (Admin) super.deserialize(adminJSON);

            JSONArray dependantsJSON = adminJSON.getJSONArray("dependants");
            for (int i = 0; i < dependantsJSON.length(); i++) {
                JSONObject dependantJSON = dependantsJSON.getJSONObject(i);

                dependants.add(serializer.deserialize(dependantJSON));
                admin.setDependants(dependants);
            }

            return admin;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Serializes the class Admin.
     * @return a JSONObject representation of the class.
     * */
    public JSONObject serialize(Admin admin) {
        JSONObject adminJSON = null;

        try {
            adminJSON = super.serialize(admin);
            JSONArray dependantsJSON = new JSONArray();

            for(Leader dependant : admin.getDependants()) {
                dependantsJSON.put(serializer.serialize(dependant));
            }

            adminJSON.put("dependants", dependantsJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return adminJSON;
    }
}
