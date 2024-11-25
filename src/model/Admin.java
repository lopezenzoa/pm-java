package model;

import java.util.HashSet;
import java.util.UUID;

public class Admin extends User {
    private HashSet<TeamMember> dependants;

    public Admin(String name, String email, String password) {
        super(name, email, password);
        this.dependants = new HashSet<>();
    }

    /**
     * Construye un objeto de tipo Administrador a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa a la clase Administrador.
     * @author Enzo.
     * */
    /*
    public Administrador(JSONObject jsonObject) {
        // Construye al usuario recibiendo el JSONObject
        super(jsonObject); // No se si es buena idea ponerlo fuera del bloque try-catch

        try {
            this.lideresACargo = new HashSet<>();

            JSONArray lideresACargoJSON = jsonObject.getJSONArray("lideresACargo");

            for (int i = 0; i < lideresACargoJSON.length(); i++) {
                JSONObject liderACargoJSON = lideresACargoJSON.getJSONObject(i);
                lideresACargo.add(new Lider(liderACargoJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

     */

    public HashSet<TeamMember> getDependants() {
        return dependants;
    }

    public void setDependants(HashSet<TeamMember> dependants) {
        this.dependants = dependants;
    }

    /**
     *
     * */
    public boolean addDependant(TeamMember member) {
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

        for (TeamMember member : dependants)
            dependantsIDs.add(member.getID());

        return dependantsIDs;
    }

    /**
     * Serializa la clase administrador.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    /*
    @Override
    public JSONObject serializar(){
        JSONObject adminJSON = null;

        try {
            adminJSON = super.serializar();
            JSONArray lideresACargoJSON = new JSONArray();

            for(Lider lider: lideresACargo){
                lideresACargoJSON.put(lider);
            }

            adminJSON.put("lideresACargo", lideresACargoJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return adminJSON;
    }

     */

    @Override
    public String toString() {
        return super.toString() +
                "dependants=" + dependants +
                '}';
    }
}
