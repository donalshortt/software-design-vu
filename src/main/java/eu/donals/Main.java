package eu.donals;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        List<String> textDisplay = new ArrayList<String>();

        UserInterface ui = UserInterface.getInstance();
        GameState gameState = GameState.getInstance();

        gameState.setName(ui.initGame());

        ui.displayGame();

        textDisplay.add("Welcome " + gameState.getPlayer().getName());
        textDisplay.add("Type 'help' for a list of commands, 'back' to bring menu back");
        textDisplay.addAll(gameState.refreshPage());

        ui.displayText(textDisplay);

        while(!gameState.getIsFinished()) {
                String input = ui.takeInput();
                gameState.updateGameState(new InputParser(input));
                System.out.println();
        }

        System.exit(0);
    }

}

