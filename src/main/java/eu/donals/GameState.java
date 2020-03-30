package eu.donals;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {

    private Player player;
    private Location location;
    private JSONArray locationList;
    private boolean isFinished;

    private static GameState state;

    static {
        try {
            state = new GameState();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private GameState() throws IOException, ParseException {
        FileReader locationsReader = new FileReader("src/main/json-files/test-map.json");
        JSONParser jsonParser = new JSONParser();

        locationList = (JSONArray) jsonParser.parse(locationsReader); //imports JSONArray of locations from json file
        location = Loader.getHomeLocation(locationList);
        isFinished = false;
    }

    public static GameState getInstance() {
        return state;
    }

    public void updateGameState(InputParser parsedInput) throws IOException {
        UserInterface ui = UserInterface.getInstance();
        switch (parsedInput.getCommand()) {
            case BACK:
                ui.displayText(refreshPage());
                break;
            case MOVE:
            case GO:
                player.move(parsedInput.getArgument());
                break;
            case LOOK:
                player.look(parsedInput.getArgument());
                break;
            case TALK:
                player.talk(parsedInput.getArgument());
                break;
            case SAY:
            case ANSWER:
                player.say(parsedInput.getArgument());
                break;
            case PAUSE:
                this.isFinished = true;
                // go to menu
                break;
            case QUIT:
                this.isFinished = true;
//                System.exit(0);
                break;
            case HELP:
                printHelpMessage();
                break;
            case INV:
                player.printInv();
                break;
            default:
                System.exit(0);
        }
        isGameOver();
    }

    public void isGameOver() throws IOException {
        List<String> one = new ArrayList<String>(Loader.allItemsOnMap());
        List<String> two = new ArrayList<String>(player.getInventory());
        Collections.sort(one);
        Collections.sort(two);
        if(one.equals(two)) {
            if(getLocation() == Loader.getFinalLocation()){
                if (Desktop.isDesktopSupported()) {
                    try {
                        addPrint("You finished the game " + player.getName() + "! Get your certificate.");
                        isFinished = true;

                        File myFile = new File("/path/to/file.pdf");
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }
            } else {
                state.addPrint("You have all the items, now go to " + Loader.getFinalLocation().getName() + " to finish the game.");
            }
        }
    }

    public List<String> refreshPage() { //maybe static
        List<String> textDisplay = new ArrayList<String>();
//        textDisplay.add("Welcome " + getPlayer().getPlayerName());
//        textDisplay.add("Type 'help' for a list of commands, 'back' to bring menu back");
        textDisplay.add(getPlayer().getName() + ", your current location: " + getLocation().getName());
        textDisplay.add(getLocation().getDescription());
        textDisplay.add("NPCs: " + location.getNPC().getName());
        textDisplay.add("Locations nearby: " + getLocation().getNeighbours());
        return textDisplay;
    }

    public void addPrint(String added) throws IOException {
        List<String> outputText = refreshPage();
        UserInterface ui = UserInterface.getInstance();
        outputText.add("***");
        outputText.add(added);
        ui.displayText(outputText);
    }

    private void printHelpMessage() throws IOException {
        List<String> outputText = new ArrayList<String>();
        UserInterface ui = UserInterface.getInstance();

        outputText.add("move/go [location] - Move to that location");
        outputText.add("look [npc] - Look at npc at current location");
        outputText.add("talk [npc] - Talk to npc at location");
        outputText.add("say/answer [answer] - Answer the riddle to get the item" );
        outputText.add("quit - Quit game");

        ui.displayText(outputText);
    }

    public Location getLocation() { return location; }
    public JSONArray getLocationList() { return locationList; }
    public Player getPlayer() { return player; }
    public boolean getIsFinished() { return isFinished; }

    public void setName(String name) { player = new Player(name); }
    public void setLocation(Location location) { this.location = location; }
}
