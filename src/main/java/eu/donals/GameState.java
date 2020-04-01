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
        FileReader locationsReader = new FileReader("src/main/resources/game-data.json");
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
                isGameOver();
                break;
            case PAUSE:
                this.isFinished = true;
                // go to menu
                break;
            case QUIT:
            case EXIT:
                this.isFinished = true;
                System.exit(0);
                break;
            case HELP:
                printHelpMessage();
                break;
            case INV:
                player.printInv();
                break;
            case ERROR:
                addPrint("Unknown command");
                break;
            default:
                System.err.println("GameState reached unknown command");
                System.exit(1);
        }
    }

    public void isGameOver() throws IOException {
        List<String> essentialItems = new ArrayList<String>(Loader.allItemsOnMap());
        List<String> playerItems = new ArrayList<String>(player.getInventory());

        Collections.sort(essentialItems);
        Collections.sort(playerItems);

        if(essentialItems.equals(playerItems)) {
            if(getLocation().getName().equals(Loader.getFinalLocation().getName())) {

                addPrint("Game Over! Thank you for playing " + player.getName()
                        + "! Here is proof of completion...");

                isFinished = true;

                if (Desktop.isDesktopSupported()) {
                    try {

                        PDFGenerator.generatePDF();

                        File myFile = new File(PDFGenerator.getFileLocation());
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        addPrint("Error creating certificate");
                    }
                }

            } else {
                state.addPrint("You have all the items, get to the " + Loader.getFinalLocation().getName());
            }
        }
    }

    public List<String> refreshPage() {
        List<String> textDisplay = new ArrayList<String>();
        textDisplay.add(getPlayer().getName() + ", your current location: " + getLocation().getName());
        textDisplay.add(getLocation().getDescription());
        textDisplay.add("NPCs: " + location.getNPC().getName());
        textDisplay.add("Locations nearby: " + getLocation().getNeighbours());
        return textDisplay;
    }

    public void addPrint(String added) throws IOException {
        List<String> outputText = refreshPage();
        UserInterface ui = UserInterface.getInstance();
        //outputText.add("***"); // TODO: fix text display bug, talk with baby
        outputText.add(added);
        ui.displayText(outputText);
    }

    private void printHelpMessage() throws IOException {
        List<String> outputText = new ArrayList<String>();
        UserInterface ui = UserInterface.getInstance();

        outputText.add("move/go [location] - Move to that location");
        outputText.add("look [npc] - Look at npc at current location");
        outputText.add("talk [npc] - Talk to npc at location");
        outputText.add("say/answer [answer] - Answer the riddle to get the item");
        outputText.add("inv - Display inventory");
        outputText.add("quit/exit - Quit game");
        outputText.add("press tab to autocomplete commands");

        ui.displayText(outputText);
    }

    public Location getLocation() { return location; }
    public JSONArray getLocationList() { return locationList; }
    public Player getPlayer() { return player; }
    public boolean getIsFinished() { return isFinished; }

    public void setName(String name) { player = new Player(name); }
    public void setLocation(Location location) { this.location = location; }
}
