package eu.donals;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader {

    private static Location parseObject(JSONObject location) {
        // parse location
        JSONObject locationObject = (JSONObject) location.get("location");
        String name = (String) locationObject.get("name");
        String desc = (String) locationObject.get("description");
        // parse npc at location
        JSONObject npcObject = (JSONObject) locationObject.get("npc");
        String npc_name = (String) npcObject.get("name");
        String temp = (String) npcObject.get("image");
        List<String> npc_image = Arrays.asList(temp.split("\n"));
        String npc_about = (String) npcObject.get("about");
        String npc_item = (String) npcObject.get("item");
        String npc_riddle = (String) npcObject.get("riddle");
        String npc_answer = (String) npcObject.get("answer");
        String npc_returnDialog = (String) npcObject.get("returnDialog");
        String neigh = (String) locationObject.get("neighbours");
        List<String> neighTokens = Arrays.asList(neigh.split(", "));

        return new Location(name, desc, npc_name, npc_image, npc_about, npc_item, npc_riddle, npc_answer, npc_returnDialog, neighTokens);
    }

    public static Location getHomeLocation(JSONArray locationList) {
        return Loader.parseObject((JSONObject) locationList.get(0));
    }

    public static Location getFinalLocation() {
        JSONArray locationList = GameState.getInstance().getLocationList();
        return Loader.parseObject((JSONObject) locationList.get(locationList.size() - 1));
    }

    public static Location parseLocationByName(String argument) {
        JSONArray locationList = GameState.getInstance().getLocationList();
        for (Object obj: locationList) {
            Location location = Loader.parseObject((JSONObject) obj);
            if (location.getName().equalsIgnoreCase(argument)) {
                return location;
            }
        }
        return null;
    }

    public static List<String> allItemsOnMap() {
        JSONArray locationList = GameState.getInstance().getLocationList();
        List<String> result = new ArrayList<String>();
        for (Object obj: locationList) {
            Location location = Loader.parseObject((JSONObject) obj);
            String theItem = location.getNPC().getItem();
            if (theItem != null && theItem.length() > 0) {
                result.add(theItem);
            }
        }
        return result;
    }

    public static JSONObject parseMetaData() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("src/main/resources/meta-data.json");

        return (JSONObject) parser.parse(reader);
    }
}
