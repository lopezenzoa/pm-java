package controller.serializers;

import model.Leader;
import model.TeamMember;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.UUID;

public class LeaderSerializer extends UserSerializer {
    TeamMemberSerializer serializer;

    public LeaderSerializer() {
        this.serializer = new TeamMemberSerializer();
    }

    /**
     * Creates a new leader using as a base a JSONObject.
     * @param leaderJSON is the JSONObject used as starting point.
     * */
    public Leader deserialize(JSONObject leaderJSON) {
        HashSet<TeamMember> dependants = new HashSet<>();
        HashSet<Integer> ongoingProjects = new HashSet<>();

        try {
            Leader leader = (Leader) super.deserialize(leaderJSON);

            for (Object projectIDJSON : leaderJSON.getJSONArray("ongoingProjects"))
                ongoingProjects.add((Integer) projectIDJSON);

            leader.setOngoingProjects(ongoingProjects);

            JSONArray dependantsJSON = leaderJSON.getJSONArray("dependants");
            for (int i = 0; i < dependantsJSON.length(); i++) {
                JSONObject dependantJSON = dependantsJSON.getJSONObject(i);
                dependants.add(serializer.deserialize(dependantJSON));
            }

            leader.setDependants(dependants);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Serializes the class Leader.
     * @return a JSONObject representation of the class.
     * */
    public JSONObject serialize(Leader leader) {
        JSONObject leaderJSON = null;

        try {
            leaderJSON = super.serialize(leader);
            JSONArray ongoingProjectsJSON = new JSONArray();
            JSONArray dependantsJSON = new JSONArray();

            for (Integer projectID : leader.getOngoingProjects()){
                ongoingProjectsJSON.put(projectID);
            }

            leaderJSON.put("ongoingProjects", ongoingProjectsJSON);

            for(TeamMember dependant : leader.getDependants()){
                dependantsJSON.put(serializer.serialize(dependant));
            }

            leaderJSON.put("dependants", dependantsJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return leaderJSON;
    }
}
