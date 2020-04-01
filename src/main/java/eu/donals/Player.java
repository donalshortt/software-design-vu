package eu.donals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<String> inventory;

    private boolean hasItem(String item) { return inventory.contains(item); }

    private boolean containsIgnoreCase(List<String> list, String soughtFor) {
        for (String current : list) {
            if (current.equalsIgnoreCase(soughtFor)) {
                return true;
            }
        }
        return false;
    }

    public String getName() { return name; }

    public void move(String argument) throws IOException {
        GameState state = GameState.getInstance();
        List<String> currNeighbours = state.getLocation().getNeighbours();

        Location location = Loader.parseLocationByName(argument);

        if(location != null) {
            if (containsIgnoreCase(currNeighbours, argument))
            {
                state.setLocation(location);
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
        UserInterface ui = UserInterface.getInstance();

        if(argument.equalsIgnoreCase(state.getLocation().getNPC().getName())) {
            ui.displayImage(state.getLocation().getNPC().getImage());
            state.addPrint(state.getLocation().getNPC().getAbout());
        } else {
            state.addPrint("NPC is not here");
        }
    }

    public void talk(String argument) throws IOException {
        GameState state = GameState.getInstance();
        UserInterface ui = UserInterface.getInstance();

        if(argument.equalsIgnoreCase(state.getLocation().getNPC().getName())) {
            ui.displayImage(state.getLocation().getNPC().getImage());
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
        UserInterface ui = UserInterface.getInstance();
        String correctAnswer = state.getLocation().getNPC().getAnswer();
        String item = state.getLocation().getNPC().getItem();

        if (correctAnswer.equalsIgnoreCase(argument)) {
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
    }
}
