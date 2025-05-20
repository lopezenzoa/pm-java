package controller.serializers;

import org.json.JSONObject;

public interface Serializable<T> {
    T deserialize(JSONObject jsonObject);
    JSONObject serialize(T type);
}
