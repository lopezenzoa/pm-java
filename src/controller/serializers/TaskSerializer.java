package controller.serializers;

import model.Task;
import model.enums.Status;
import model.enums.Visibility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class TaskSerializer implements Serializable<Task> {
    TeamMemberSerializer serializer;

    public TaskSerializer() {
        this.serializer = new TeamMemberSerializer();
    }

    @Override
    /**
     * Creates a new task using as a base a JSONObject.
     * @param taskJSON is the JSONObject used as starting point.
     * */
    public Task deserialize(JSONObject taskJSON) {
        Task task = new Task();

        try {
            task.setID(taskJSON.getInt("ID"));
            task.setProjectID(taskJSON.getInt("projectID"));
            task.setTitle(taskJSON.getString("title"));
            task.setDescription(taskJSON.getString("description"));
            task.setResponsible(serializer.deserialize(taskJSON.getJSONObject("responsible")));
            task.setCreationDate(taskJSON.getString("creationDate"));
            task.setDeadline(taskJSON.getString("deadline"));
            task.setStatus(Status.valueOf(taskJSON.getString("status")));
            task.setVisibility(Visibility.valueOf(taskJSON.getString("visibility")));

            return task;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    /**
     * Serializes the class Task.
     * @return a JSONObject representation of the class.
     * */
    public JSONObject serialize(Task task) {
        JSONObject taskJSON = null;

        try {
            taskJSON = new JSONObject();

            taskJSON.put("ID", task.getID());
            taskJSON.put("projectID", task.getProjectID());
            taskJSON.put("title", task.getTitle());
            taskJSON.put("description", task.getDescription());
            taskJSON.put("responsible", serializer.serialize(task.getResponsible()));
            taskJSON.put("creationDate", task.getCreationDate());
            taskJSON.put("deadline", task.getDeadline());
            taskJSON.put("status", task.getStatus());
            taskJSON.put("visibility", task.getVisibility());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return taskJSON;
    }
}
