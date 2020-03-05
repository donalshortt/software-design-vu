import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LocationLoader {

    public static Location parseJsonObject(JSONObject location) {
        JSONObject locationObject = (JSONObject) location.get("location");

        String name = (String) locationObject.get("name");
        String desc = (String) locationObject.get("description");

        JSONObject npcObject = (JSONObject) locationObject.get("npc");

        String npcName = (String) npcObject.get("name");
        String npcAbout = (String) npcObject.get("about");
        String npcItem = (String) npcObject.get("item");
        String npcRiddle = (String) npcObject.get("riddle");
        String npcAnswer = (String) npcObject.get("answer");
        String npcReturnDialog = (String) npcObject.get("returnDialog");

        String items = (String) locationObject.get("items");
        String[] itemTokens = items.split(", ");

        String neigh = (String) locationObject.get("neighbours");
        String[] neighTokens = neigh.split(", ");

        return new Location(name, desc, npcName, npcAbout, npcItem, npcRiddle, npcAnswer, npcReturnDialog, itemTokens, neighTokens);
    }

    public Location getHomeLocation(JSONArray locationList) {
        return LocationLoader.parseJsonObject((JSONObject) locationList.get(0));
    }

    public Location parseJsonByName(String argument, JSONArray locationList) {
        for (Object obj : locationList) {
            Location location = LocationLoader.parseJsonObject((JSONObject) obj);

            if (location.getName().equals(argument)) {
                return location;
            }
        }
        return null;
    }

}