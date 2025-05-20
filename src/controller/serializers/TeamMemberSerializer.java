package controller.serializers;

import model.TeamMember;
import model.enums.Role;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class TeamMemberSerializer extends UserSerializer {
    public TeamMemberSerializer() {}

    /**
     * Creates a new team member using as a base a JSONObject.
     * @param teamMemberJSON is the JSONObject used as starting point.
     * */
    public TeamMember deserialize(JSONObject teamMemberJSON) {
        HashSet<Integer> ongoingProjects = new HashSet<>();

        try {
            TeamMember teamMember = (TeamMember) super.deserialize(teamMemberJSON);

            for (Object projectIDJSON : teamMemberJSON.getJSONArray("ongoingProjects"))
                ongoingProjects.add((Integer) projectIDJSON);

            teamMember.setOngoingProjects(ongoingProjects);
            teamMember.setRole(Role.valueOf(teamMemberJSON.getString("role")));

            return teamMember;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Serializes the class TeamMember.
     * @return a JSONObject representation of the class.
     * */
    public JSONObject serialize(TeamMember teamMember) {
        JSONObject memberJSON = null;

        try {
            memberJSON = super.serialize(teamMember);
            JSONArray ongoingProjectsJSON = new JSONArray();

            for (Integer projectID : teamMember.getOngoingProjects())
                ongoingProjectsJSON.put(projectID);

            memberJSON.put("ongoingProjects", teamMember.getOngoingProjects());
            memberJSON.put("role", teamMember.getRole());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return memberJSON;
    }
}
