package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class Admin extends User {
    private HashSet<Leader> dependants;

    public Admin(String name, String email, String password) {
        super(name, email, password);
        this.dependants = new HashSet<>();
    }

    /**
     * Construye un objeto de tipo Administrador a partir de un objeto de tipo JSONObject.
     * @param adminJSON es el objeto en formato JSON que representa a la clase Administrador.
     * @author Enzo.
     * */
    public Admin(JSONObject adminJSON) {
        super(adminJSON);

        try {
            this.dependants = new HashSet<>();

            JSONArray dependantsJSON = adminJSON.getJSONArray("dependants");
            for (int i = 0; i < dependantsJSON.length(); i++) {
                JSONObject dependantJSON = dependantsJSON.getJSONObject(i);
                dependants.add(new Leader(dependantJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Leader> getDependants() {
        return dependants;
    }

    public void setDependants(HashSet<Leader> dependants) {
        this.dependants = dependants;
    }

    /**
     *
     * */
    public boolean addDependant(Leader member) {
        if (!dependants.contains(member)) {
            dependants.add(member);
            return true;
        }
        return false;
    }

    /**
     *
     * */
    public boolean removeDependant(TeamMember member) {
        return dependants.remove(member);
    }

    /**
     *
     * */
    public HashSet<UUID> getDependantsIDs() {
        HashSet<UUID> dependantsIDs = new HashSet<>();

        for (Leader dependant : dependants)
            dependantsIDs.add(dependant.getID());

        return dependantsIDs;
    }

    /**
     * Serializa la clase administrador.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    @Override
    public JSONObject serialize(){
        JSONObject adminJSON = null;

        try {
            adminJSON = super.serialize();
            JSONArray dependantsJSON = new JSONArray();

            for(Leader dependant : dependants){
                dependantsJSON.put(dependant.serialize());
            }

            adminJSON.put("dependants", dependantsJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return adminJSON;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", dependants=" + dependants +
                '}';
    }
}
