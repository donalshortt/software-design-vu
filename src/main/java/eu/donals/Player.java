package eu.donals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<String> inventory;
//    private UserInterface ui;

    private boolean hasItem(String item) { return inventory.contains(item); }

    public String getName() { return name; }

    public void move(String argument) throws IOException {
        GameState state = GameState.getInstance();
        List<String> currNeighbours = state.getLocation().getNeighbours();

        Loader locationLoader = new Loader();
        Location location = locationLoader.parseLocationByName(argument);

        if(location != null) {
            if (currNeighbours.contains(argument)) {
                state.setLocation(location);
//                List<String> outputText = state.refreshPage();
//                ui.displayText(outputText);
                state.addPrint("So, what's next?");
            } else {
                state.addPrint("No such place in this neighbourhood");
            }
        } else {
            state.addPrint("No such place in this game");
        }
    }

    public void look(String argument) throws IOException {
        GameState state = GameState.getInstance();
        if(argument.equals(state.getLocation().getNPC())) {
            state.addPrint(state.getLocation().getNPC().getAbout());
        } else {
            state.addPrint("NPC is not here");
        }
    }

    public void talk(String argument) throws IOException {
        GameState state = GameState.getInstance();
        if(argument.equals(state.getLocation().getNPC().getName())) {

            if(hasItem(state.getLocation().getNPC().getItem())) {
                state.addPrint(state.getLocation().getNPC().getReturnDialog());
            } else {
                state.addPrint(state.getLocation().getNPC().getRiddle());
            }
        } else {
            state.addPrint("NPC is not here");
        }
    }

    public void say(String argument) throws IOException {
        GameState state = GameState.getInstance();
        String correctAnswer = state.getLocation().getNPC().getAnswer();
        String item = state.getLocation().getNPC().getItem();

        if (correctAnswer.equals(argument)) {
            if(hasItem(item)) {
                state.addPrint("You already have " + item + " in your inventory");
            } else {
                inventory.add(item);
                state.addPrint(item + " added to inventory");
                state.isGameOver();
            }
        } else {
            state.addPrint("Incorrect answer");
        }
    }

    public void printInv() throws IOException { GameState.getInstance().addPrint("Inventory: " + getInventory()); }

    public List<String> getInventory() { return inventory; }

    public Player(String name) {
        this.name = name;
        inventory = new ArrayList<String>(){};
//        ui = UserInterface.getInstance();
    }
}
