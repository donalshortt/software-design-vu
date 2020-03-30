package eu.donals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> textDisplay = new ArrayList<String>();

        UserInterface ui = UserInterface.getInstance();
        GameState gameState = GameState.getInstance();

        gameState.setName(ui.initGame());

        ui.displayGame();

        textDisplay.add("Welcome " + gameState.getPlayer().getName());
        textDisplay.add("Type 'help' for a list of commands, 'back' to bring menu back");
        textDisplay.addAll(gameState.refreshPage());
//        textDisplay.add("Current location: " + gameState.getLocation().getName());
//        textDisplay.add("Neighbouring locations: " + gameState.getLocation().getNeighbours());

        ui.displayText(textDisplay); //(gameState.refreshPage());
//        System.out.println(Loader.allItemsOnMap());
        while(!gameState.getIsFinished()) {
            try {
                String input = ui.takeInput();
                gameState.updateGameState(new InputParser(input));
                System.out.println(); //newline
            }
            catch (Exception e){
//                tempDisplay = textDisplay;
//                tempDisplay.add(e.getMessage());
                gameState.addPrint("Something went wrong");
            }
        }
    }
}

