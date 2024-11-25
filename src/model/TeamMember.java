package model;

import javax.management.relation.Role;
import java.util.HashSet;

public class TeamMember extends User {
    private HashSet<Project> ongoingProjects;
    private Role role;

    public TeamMember(String name, String email, String password, Role role) {
        super(name, email, password);
        this.ongoingProjects = new HashSet<>();
        this.role = role;
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
     * Serializa la clase MiembroEquipo.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    /*
    @Override
    public JSONObject serializar() {
        JSONObject miembroJSON = null;

        try {
            // Llama al metodo de la clase padre para evitar redundancia en el codigo
            miembroJSON = super.serializar();
            JSONArray proyectosEnCursoJSON = new JSONArray();

            for (int idProyecto : proyectosEnCurso)
                proyectosEnCursoJSON.put(idProyecto);

            miembroJSON.put("proyectosEnCurso", proyectosEnCursoJSON);
            miembroJSON.put("rol", rol.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return miembroJSON;
    }

     */

    @Override
    public String toString() {
        return super.toString() +
                ", ongoingProjects=" + ongoingProjects +
                ", role=" + role +
                '}';
    }
}
