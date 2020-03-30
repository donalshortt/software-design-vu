package eu.donals;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader {

    private static Location parseLocationObject(JSONObject location) {
        JSONObject locationObject = (JSONObject) location.get("location");

        String name = (String) locationObject.get("name");
        String desc = (String) locationObject.get("description");

        JSONObject npcObject = (JSONObject) locationObject.get("npc");

        String npc_name = (String) npcObject.get("name");
        String npc_about = (String) npcObject.get("about");
        String npc_item = (String) npcObject.get("item");
        String npc_riddle = (String) npcObject.get("riddle");
        String npc_answer = (String) npcObject.get("answer");
        String npc_returnDialog = (String) npcObject.get("returnDialog");

        String neigh = (String) locationObject.get("neighbours");
        List<String> neighTokens = Arrays.asList(neigh.split(", "));

        return new Location(name, desc, npc_name, npc_about, npc_item, npc_riddle, npc_answer, npc_returnDialog, neighTokens);
    }

    public static Location getHomeLocation(JSONArray locationList) {
//        JSONArray locationList = GameState.getInstance().getLocationList();
        return Loader.parseLocationObject((JSONObject) locationList.get(0));
    }

    public static Location getFinalLocation() {
        JSONArray locationList = GameState.getInstance().getLocationList();
        return Loader.parseLocationObject((JSONObject) locationList.get(locationList.size()-1));
    }

    public Location parseLocationByName(String argument) {
        JSONArray locationList = GameState.getInstance().getLocationList();
        for (Object obj: locationList) {
            Location location = Loader.parseLocationObject((JSONObject) obj);

            if (location.getName().equals(argument)) {
                return location;
            }
        }
        return null;
    }

    public static List<String> allItemsOnMap() {
        JSONArray locationList = GameState.getInstance().getLocationList();
        List<String> result = new ArrayList<String>();
        for (Object obj: locationList) {
            Location location = Loader.parseLocationObject((JSONObject) obj);
            String theItem = location.getNPC().getItem();
            if (theItem != null && theItem.length() > 0) {
                result.add(theItem);
            }
        }
        return result;
    }

    public List<String> parseImage() {
        // TODO: implement me!
        return null;
    }

}