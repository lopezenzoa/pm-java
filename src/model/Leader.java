package model;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.UUID;

public class Leader extends User {
    private HashSet<Project> ongoingProjects;
    private HashSet<TeamMember> dependants;

    public Leader(String name, String email, String password) {
        super(name, email, password);
        this.ongoingProjects = new HashSet<>();
        this.dependants = new HashSet<>();
    }

    /**
     * Construye un objeto de tipo MiembroEquipo del equipo a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa a la clase MiembroEquipo.
     * @author Enzo.
     * */
    /*
    public MiembroEquipo(JSONObject jsonObject) {
        // Construye al usuario recibiendo el JSONObject
        super(jsonObject); // No se si es buena idea ponerlo fuera del bloque try-catch

        try {
            this.ongoingProjects = new HashSet<>();

            for (Object idProyectoJSON : jsonObject.getJSONArray("proyectosEnCurso"))
                ongoingProjects.add(Integer.parseInt(idProyectoJSON.toString()));

            String rolJSON = jsonObject.getString("rol");
            this.role = Rol.valueOf(rolJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

     */

    public HashSet<Project> getOngoingProjects() {
        return ongoingProjects;
    }

    public void setOngoingProjects(HashSet<Project> ongoingProjects) {
        this.ongoingProjects = ongoingProjects;
    }

    public HashSet<TeamMember> getDependants() {
        return dependants;
    }

    public void setDependants(HashSet<TeamMember> dependants) {
        this.dependants = dependants;
    }

    /**
     *
     * */
    public boolean addOngoingProject(Project project) {
        if (!ongoingProjects.contains(project)) {
            ongoingProjects.add(project);
            return true;
        }
        return false;
    }

    /**
     *
     * */
    public boolean removeOngoingProject(Project project) {
        return ongoingProjects.remove(project);
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
     * Serializa la clase lider.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    /*
    @Override
    public JSONObject serializar(){
        JSONObject liderJson = null;

        try {
            liderJson = super.serializar();
            JSONArray proyectosEnCursoJSON = new JSONArray();
            JSONArray miembrosACargoJSON = new JSONArray();

            for (String nombreProyecto : proyectosEnCurso){
                proyectosEnCursoJSON.put(nombreProyecto);
            }

            liderJson.put("proyectosEnCurso", proyectosEnCursoJSON);

            for(MiembroEquipo miembro: miembrosACargo){
                miembrosACargoJSON.put(miembro.serializar());
            }

            liderJson.put("miembrosACargo", miembrosACargoJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return liderJson;
    }

     */

    @Override
    public String toString() {
        return super.toString() +
                ", ongoingProjects=" + ongoingProjects +
                ", dependants=" + dependants +
                '}';
    }
}
