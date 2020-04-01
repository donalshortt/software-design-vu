# Assignment 3

## Summary of changes of Assignment 2

Author: `Donal Shortt`

* Fixed the inconsistencies in the class diagram.
** Return values now displayed correctly
** Constructors that did not provide any useful information in the class diagram have been removed
** InputParser class has been added to the CD
* Class diagram description now includes association type and multiplicities
* Object diagrams now consistent with class diagram
* State machine diagrams were of stateless classes, this has been fixed
** GameState sequence diagrams have been improved
* Sequence diagrams now consistent with class diagram
** The correct arrow syntax for diagrams has been used
** The diagrams are now easier to follow
* Jar file was previously located in the "target" folder, now located in the root folder

## Application of design patterns

Author: `Donal Shortt`

![Annotated_Class_Diagram](https://i.imgur.com/aert0LT.png)

|ID|DP-1|
|-|-|
|Design Pattern|Singleton|
|Problem|There should only ever be one instance of gamestate in existence at any given time. If multiple instances were accidentally created, it could be very confusing to figure out why some information is accessible while other information that should be available is not.|
|Solution|The singleton design pattern ensures there is only one instance of a class available by making the constructor a private method. This solves our problem as now we can be sure no one can create a new instance of GameState, they can only access the reference to the instance.|
|Intended Use|GameState's purpose is to store the state of the game. As such, if there were multiple instances or states things would get very confusing very quickly. An instance of GameState will simply store the relevant information and update itself when the player takes an action.|
|Constraints|In this case the singleton design pattern does not impose any additional constraints as the contraints it already imposes are ones that our program intends to follow anyway.|
|Additional Remarks|N/A|

|ID|DP-2|
|-|-|
|Design Pattern|Singleton|
|Problem|There should only be one instance of UserInterface is existence at any given time. If multiple instances of UserInterface existed, the game display would probably look very strange as information would not be cleared properly and possible some information would not be displayed.|
|Solution|The singleton design pattern solves this issue by ensuring that only one instance of the UserInterface can exist at one time.|
|Intended Use|UserInterface is intended to be as the name suggests, the bridge between the user and the game logic. It is meant to display relevant information to the user and accept any input they provide. Since the singleton enforces only one instance, we know for sure that all information sent to the player will get displayed to them and not lost somewhere, and vice versa.|
|Constraints|As with the previous case, this design pattern does not impose any constraints other than that we can only have one instance of the class in existence. But since this was intended behavior anyway it doesn't matter.|
|Additional Remarks|N/A|

## Class Diagram

Author: `Donal Shortt`

![Class_Diagram_4](https://i.imgur.com/LI7gG1J.jpg)

### Main

- Represents the entry point of the program and runs the game loop

#### Attributes
* None

#### Operations

* **main**
-- The entry point of the program. Initialises the perquisite objects and runs the game loop.

#### Associations

* **UserInterface (General Association)**
-- UserInterface is the class that represents the front end of the game. Main needs to interact with this in order to display information to the player and to take information from the player to give to GameState.
* **GameState (Composite Association) [1 <-> 1]**
-- GameState is the class that represents the back end of the game. Main needs to interact with this in order to to play the game and pass information about the state of the game to the UserInterface.
* **Loader (Composite Association) [1 <-> 1]**
-- Loader fulfills the role of a utility class. It loads information from JSON files to send to both the UserInterface and the Gamestate
* **InputParser (General Association)**
-- InputParser is responsible for parsing user input. It is connected to main so that input collected from the UserInterface may be passed it, and then from there passed to various classes that need it, such as GameState.

### UserInterface

- Represents the front end of the game. Displays information to the user through text and text based images via the text display and image display boxes. Takes information from the user via the input box.

#### Attributes

* **terminal**
-- Terminal is a class provided by the lanterna library.  It is at the bottom of the stack that lanterna provides to display information on the terminal. Our project does not interact with the Terminal class directly, rather it uses Screen. However to utilise Screen, it must be initialised on top of the Terminal class.
* **screen**
-- Screen is a class provided by the lanterna library. It is a step above terminal in the stack provided by lanterna and it is this class that our project uses to provide information to the player. Information is displayed to the player by writing to the screen's back buffer and then flushing these changes to the front buffer.
* **tg**
-- TextGraphics is a class provided by the lanterna library. It is a utility class that provides several functions to assist with drawing graphics to the screen's back buffer. In particular. we use this class to draw the boxes of the input and display boxes.
	It also makes it much easier to display strings on the screen. With it we can push entire strings onto the screen, without we would need to write to the screen character by character.
player: Player

#### Operations

* **UserInterface.getInstance**
-- Returns a reference to the internally stored object of UserInterface. This method is here to enforce the singleton design pattern
* **UserInterface.initGame**
-- Initialises the game by displaying the game name and then prompting the player for their character's name.
* **UserInterface.displayGame**
-- Displays the actual game UI in it's default configuration. Displays the display boxes and input box in an empty state.
* **UserInterface.displayImage**
-- Takes in a list of strings and then displays each string on a new line within the image display box.player: Player
* **UserInterface.displayText**
-- This is an overloaded method. Can take in either a single string to display in the text display box, or it can take in a list of strings to display on the text display box. Each string will be display on a new line.
* **UserInterface.takeInput**
-- Used to take input from the input box and pass the result as a string to the calling method or function. This function basically just wraps the readUserInput function and supplies it with the correct coordinates to take input from the input box. This is a blocking method, meaning that execution will pause until input is taken.
* **UserInterface.autocompleteUserInput**
-- Based on the first character of the current user input, autocomplete will fill the the command that it thinks the user wishes to type.
* **UserInterface.readUserInput**
-- This is a utility function which reads userInput at the TerminalPosition supplied in the arguments. This is a blocking method.
* **UserInterface.centreText**
-- This is a utility function used to centre a string horizontally within the screen. It is used for centering the text that is displayed when the game is being initialised.
* **UserInterface.stringSplit**
-- This function takes a string that is too long to fit in the text display box and attempts to split it into a List of smaller strings.


#### Associations

* **Main - (General Association)**
-- Already described
* **GameState - (General Association)**
-- GameState is the class that represents the current state of the game. GameState needs to access the UserInterface class in order to display certain information about the state of the game to the player.
* **Player - (General Association)**
-- Player is the class that represents the player. Player needs to access userInterface hen it wants to display the results of some action the player has taken.
* **DisplayError - (Composite Association) [1 <-> 1]**
-- DisplayError is an error handling class that extends the Java Exception class. It is used to handle errors thrown by UserInterface when the text trying to be displayed won't fit within the boxes provided.

### Loader

- Loader is a utility class used by main to load data from the game's JSON files into the game state.

#### Attributes
* None

#### Operations

* **Loader.parseObject**
-- Parses the JSON object from the JSON file and returns a Location object.
* **Loader.getHomeLocation**
-- Returns the first location in the gamedata JSON file, used as the "home" or starting location.
* **Loader.getFinalLocation**
-- Returns the last location in the gamedata JSON file, used as an area to go to when the user is ready to complete the game.
* **Loader.parseLocationByName**
-- Searches the JSON file for the location with the specified name and returns it. Basically just wraps the parseLocationObject().
* **Loader.allItemsOnMap**
-- Used to find all the items on the map. This is necessary to decide whether the user can win the game or not.
* **Loader.parseMetaData**
-- Parses the JSON object from the JSON file and returns the metadata of the game, used in generating a PDF when the game is over, along with displaying information when the game is starting.

#### Associations

* **Main (Composite Association) [1 <-> 1]**
-- Already described

### GameState

- Represents the current state of the game, or in other words, the game's current configuration

#### Attributes
* **player**
-- Holds a reference to the instance of the Player class.
* **location**
-- Holds a reference to the instance of the current location.
* **locationList**
-- Holds a reference to the list of locations that have been loaded in by the Loader class.
* **isFinished**
-- A boolean value that evaluates to true when the winning conditions for the game have been met.

#### Operations
* **GameState.getInstance**
-- Returns a reference to the internally stored object of GameState. This method is here to enforce the singleton design pattern
* **GameState.updateGameState**
-- This method is called every time the player interacts with the game in such a so that a game state change is required. An example would be when the player decides to change location.
* **GameState.isGameOver**
-- If the game is over, this method is called. It tells the user they have won the game and generates a certificate for them
* **GameState.refreshPage**
-- Used to refresh the info available to the player after they have finished with the help command
* **GameState.printHelpMessage**
-- Displays a list of commands available to the player via the text display box on the userInterface
* **GameState.getLocation**
-- Gets the current location in use by the game
* **GameState.getLocationList**
-- Gets the current list of locations that have been loaded in by the loader class
* **GameState.getPlayer**
-- Gets the player instance in use by the gameState instance
* **GameState.getIsFinished**
-- Returns a Boolean value as to whether or not the winning conditions for the game have been met
* **GameState.setName**
-- Sets the name of the player after they are prompted for it in the game init phase
* **GameState.setLocation**
-- Sets the current location to the new location supplied in the arguments

#### Associations
* **Main (Composite Association) [1 <-> 1]**
-- Already described
* **UserInterface (General Association)**
-- Already Described
* **simple.JSONArray (Composite Association) [1 <-> 1]**
-- JSONArray is a way to store a number of JSON objects within an array-like data structure. Gamestate uses it to store the list of loaded Locations.
* **Player (Composite Association) [1 <-> 1]**
-- Player is the class that represents The class is a singleton.the player in the game. Gamestate needs to access Player in order to know how it should update it's own state.
* **Location (Composite Association) [1 <-> 1]**
-- Location is the class that represents the Location that the player is in. GameState needs to access it in order to get certain information when a gameStateUpdate is required. Such information could be the neighboring Locations or the NPCs located in that Location
* **InputParser (General Association)**
-- Input parser is used to parse user input. GameState relies on it to parse the user input into a format that it can understand, or to figure out what the user is trying to do.
* **PDFGenerator (General Association)**
-- PDFGenerator is used by the isGameOver method to generate a certificate for the user once they have completed the game.

### Player

- The player class represents the player within the game. It contains all the methods used when the player interacts with the game in any way, with the exception of printHelpMessage().

#### Attributes
* **name**
-- The name of the player's character.
* **inventory**
-- A list of strings representing the inventory of the player, each string representing an item.

#### Operations
* **Player.hasItem**
-- Returns a boolean value as to whether the Player has a certain item in their inventory or not.
* **Player.containsIgnoreCase**
-- Returns true if a provided string is contained within a provided list of strings. Ignoring case.
* **Player.getName**
-- Returns the player's name.
* **Player.move**
-- Moves the player to a new location via a GameState update.
* **Player.look**
-- Looks at an NPC within the location.
* **Player.talk**
-- Initiates a conversation with an NPC.
* **Player.say**
-- Answers an NPC's riddle.
* **Player.printInv**
-- Prints a list of items in the player's inventory via the UserInterface.
* **Player.getInventory**
-- Returns a list of Strings representing the player's inventory.


#### Associations
* **GameState (Composite Association) [1 <-> 1]**
-- Already described
* **UserInterface (General Association)**
-- The Player class needs to interact with the UserInterface class when it needs to convey information the the player. Such as printing the player's inventory or looking at NPCs.

### Location

- The Location class represents the current Location that the player is inhabiting. It contains the NPCs found in that particular location, along with the items that can be obtained from the location.

#### Attributes
* **name**
-- The name of the NPC
* **description**
-- A string describing the location.
* **npc**
-- A reference to the NPC private nested class.
* **neighbours**
-- An array of strings of neighboring locations to the current location.

#### Operations
* **Location.getName**
-- Gets the name of the location.
* **Location.getDescription**
-- Gets the description of that location.
* **Location.getNPC**
-- Returns a reference to the instance of the NPC class nested within this class.
* **Location.getNeighbours**
	-- Returns the neighboring locations to the current location.

#### Associations
* **GameState (Composite Association) [1 <-> 1]**
-- Already described.
* **NPC (Composite Association) [1 <-> 1]**
-- Location uses the private nested class of NPC to represent the information about the npc located in that location.

### NPC

-- NPC is a nested class located within the Location class. It does not provide any other functionality other than to group the data about the NPC together.

#### Attributes
* **name**
-- The name of the NPC.
* **image**
-- The image to be displayed when looking at the NPC.
* **about**
-- A description of the NPC.
* **item**
-- The item the NPC will give the player after the player has solved their riddle.
* **riddle**
-- The riddle the NPC offers to the player
* **answer**
-- The answer to the riddle offered by the NPC
* **returnDialog**
-- The dialog the NPC displays when the player talks to the NPC

#### Operations
* **NPC.getName**
-- Returns the name of the NPC.
* **NPC.getImage**
-- Returns the image to be displayed when looking at the NPC.
* **NPC.getAbout**
-- Returns the description of the NPC.
* **NPC.getRiddle**
-- Returns the NPC's riddle.
* **NPC.getReturnDialog**
-- Returns the dialog that the NPC gives when the player talks to the NPC.
* **NPC.getAnswer**
-- Returns the answer to the NPCs riddle
* **NPC.getItem**
-- Returns the name of the item that is held by the NPC.

#### Associations
* **Location (Composite Association) [1 <-> 1]**
-- Already described.

### DisplayError
-- Display error is a custom exception class created to handle exceptions thrown by the UserInterface class. The exception is thrown if the text to be displayed in either the text display box or image display box does not fit within the box.

#### Attributes
-- None.

#### Operations
* **DisplayError**
-- Constructor Method. Used to pass the error message to the superclass.

#### Associations
* **UserInterface (Composite Association) [1 <-> 1]**
-- Already described.

### PDFGenerator
-- PDFGenerator is a class used to generate the certificate for the user once they complete the game successfully.

#### Attributes
* **fileLocation**
-- Stores the filepath for the certificate.

#### Operations
* **addMetaData**
-- Adds the relevant metadata to the certificate. Not to be confused with game metadata.
* **addContent**
-- Add the relevant information to the certificate.
* **generatePDF**
-- Generates the PDF, calls on addContent to add the relevant content.
* **getFileLocation**
-- Returns the fileLocation.

#### Associations

* **GameState (General Association)**
-- Already described
* **itextpdf.text (Composite Association) [1 <-> 1]**
-- Used to design the the certificate in the form of a PDF. Includes various methods for formatting a PDF in java.

### External Classes

#### simple.JSONArray
-- This class is used to hold an array of JSON objects. We use JSON files to represent all the data associated with the game.
#### lanterna.terminal.Terminal
-- Terminal is a class used by the userInterface to display game output to the user.
#### lanterna.screen.Screen
-- Screen is an extension of Terminal and i used to display output to the user in more advanced formats.
#### lanterna.graphics.TextGraphics
-- TextGraphics is a library that allows us to display simple text-based graphics to the user.
#### itextpdf.text
-- This package contains various classes used to format and generate PDF documents using Java.

### InputParser
-- InputParser is a class used to parse user input into a format that is understood by other classes within the system. It is also used to parse which command the user has executed.

#### Attributes
* **command**
-- The command typed in by the user.
* **argument**
-- The command arguments typed in by the user.

#### Operations
* **getCommand**
-- Returns the command typed in by the player.
* **getArgument**
-- Returns the arguments to the command typed in bu the user.
* **parseCommand**
-- Figures out which command, if any, was typed in by the user.
* **isCommand**
-- Returns a boolean as to whether or not the user input is a command.
* **parseArugment**
-- Returns the arguments in a format that is understood by various other methods.

#### Associations
* **GameState (General Association)**
-- Already described.
* **Main (General Association)**
-- Already described.

### Previous Class Diagrams

#### Version 1
![Class_Diagram_1](https://imgs.su/upload/271/3179159392.jpg)

The first version of the class diagram was made just to get a sense of what the system would look like. The UML syntax is not correct in this version as the purpose was to establish a rough draft of the system.

#### Version 2
![Class_Diagram_2](https://imgs.su/upload/271/2758094369.jpg)

In the second version GameState was split up into game and state. We did this because we thought it would make sense to have the game logic handled in one class and the state of the game stored in another. The UserInterface class does still not exist in this diagram as we were still unsure on how to use Lanterna.

#### Version 3
![Class_Diagram_3](https://imgs.su/upload/271/603796206.jpg)

Game and State had been put back into the same class and UserInterface had been modeled. The external libraries are still missing from this version however. LocationLoader, which will become Loader has also been moved to exist within the main class as it will be needed to set up the game, which is the responsibility of Main.

#### Version 4
![Class_Diagram_4](https://i.imgur.com/WmlXEhS.jpg)

The fourth version is the most similar to the current version. It is the same version used for assignment 2. The diagram has been expanded to include necessary third-party libraries and colour had been added to make the diagram clearer.

# Object Diagrams

Author(s): *Nariman Gasimov*
![object diagram](https://imgs.su/upload/277/2378765480.png)
This figure represents the 5 main objects during the execution of the game. When a player enters a command *“go market”* the **InputParser** object divides that input into command(go) and argument(market) parts. The parsing methods of this class also take care of case sensitivity and command checking in case of incorrect input.  Then the **GameState** object using the switch statements picks up the corresponding method to call from the **Player** object. In this case the *Player.move()* method is called with the user argument and if the selected location is in the neighbourhood the gamestate object is updated with the new Location. In this case, the user asked to go to the "market" and therefore the respective piece of data from the list of locations is loaded into the **Location** object. Setting the name to Market, adding the description, adding the possible items that can be acquired in that particular location, as well as the neighbors that are surrounding the given location. This by itself also sets the **NPC** object inside the Location object to the respective object given in the JSON file. All the information about the npc, such as the *name, ascii image of the non-player character, about, the item that it holds, the dialogues and the answers expected* are loaded into that object.

### Feedback from Assignment 2:
The issue is now resolved, the object diagram is now consistent with the class diagram.


#  Implementation
Author(s): *Nariman Gasimov*
Main class() directory: ./src/main/java/eu/donals/Main.java
.JAR directory: ./target/TextAdventureGame-1.0-jar-with-dependencies.jar
<iframe src="https://drive.google.com/file/d/16xCaygc7-wP9_PL9fVfl5xhOdtoe6i4d/preview" width="640" height="480"></iframe>

## The strategy
UML model was helpful with structuring the code. As it represents the skeleton of the system, it was easier to put in code the classes and objects. Starting from the main class and moving to GameState class and then the Player and Location classes. The strategy we had was to start with bigger classes and write slowly the methods and variables inside. The methods at first would be written in just pseudo code as comments. Later as we proceeded and had a clearer vision of how the actual methods would work, we started to write the actual code.

## Key solutions in the implementation

### Earlier implementation in assignment 2:

The key solution for our implementation was to do the commands given by the user in the **Player** class. The problem at first was that some of the commands needed to be in the **GameState** class, such as “move” command. As it needed to load a parsed json data to the Location object, this could only be done inside the **GameState** class. Our solution for this was to pass the current game state to the methods inside player class for them to access the methods inside the gamestate, thus changing the game state. Now the commands entered by the user are being categorized in **GameState** class but handled in **Player** class. We did this because it made more sense for the "player" to do the actions such as “move” or “talk” or “look” instead of being done in an object that is called after the state of the game. We also implemented alternative action commands such as go (for move) and answer (go) for user friendliness of the game.

One of the other issues that we had to tackle was the way to put the switch cases as it was not possible to do string comparison in our version of java. After doing and trying several methods we decided to use enums as the values for commands entered by the user. Another issue that appeared during implementation of this was that when a user would enter an unknown command, the compiler would crash before reaching the default case because there did not exist the enum that the user entered. We used try and catch block to tackle and solve this issue.

Main class only parses the json files, sets the starting location as the first location in the json file (index 0) and starts an instance of a GameState, later it only asks for user input and keeps updating the GameState object with the arguments that user put. The whole game’s state is dictated by the GameState class. It represents the state of the game and holds all the necessary objects inside, privately. The objects inside the GameState are player, location, locationList and isFinished.

### Current final implementation:

After deciding to use singleton design pattern for the the **GameState** class, we had to change the sturcture a little bit. Previously explained way of passing the current **gameState** object to the methods inside **Player** class was now unnecessary. Because the singleton design pattern helps to have only one instance of the given object, therefore it is unimportant to pass the **gameState** object to every method it as an argument. As there is only one instance of the state of the game at any given time, other objects can easily access it by calling the *getInstance()* method from the **GameState** object.

We also added several other commands as enums to make the game more accessible and user friendly. Now, different that before user can type "back" to refresh the page, type "inv" to have a look at their inventory and type "exit" or "quit" to exit the game.

Instead of throwing and error and catching it later, we decided that it would be more optimal to check first if the given input is a command or not. Later if the entered command appears to be an unknown command it returns "error" which just informs the user that the command could not be recognized so they can type in another one.

**Main** class
Different from the assignment 2, now **Main** class initializes the instances of **UserInterface** and **GameState** which are used to present the graphical interface and run the game engine, respectively. After this, the rest of the class is only the main loop that keeps calling the *updateGameState()* method of the **GameState** object. Parsing the json file for the map of the game is done in the constructor of the **GameState** class as it was more suitable for there.

**GameState** class
In the constructor the singleton instance of the **GameState** object is loaded with the necessary information for the rest of the game. Static methods of the **Loader** class are user to parse and load the data from the "game-data.json" file to the *locationList*. The first location provided in the json is set as the location the game starts in.

 - *updateGameState()* this method is called from the **Main** class and is updated constantly through a loop until the game is over. Basically this is the method that keeps updating the state of the game with the changes made by the reflecting the user input.
 - *isGameOver()* this method is called every time when user answers to a riddle, and it checks if the user has all the possible inventory in the game, which is the goal to finish the game. The user needs to be in the final location of the game and needs to answer to the last question(riddle) in order to finish the game and claim their certificate. The certificate is generated using the **PDFGenerator** class. The generated PDF file is also called to open.

JSONArray *locationList*  holds the parsed json’s elements inside as a json array. This array is used later in the game to set the current location of the player as they move along the map. LocationList is not changed throughout the game.

**Location** class *location* holds the current location of the player. The value is loaded as the player changes location inside the game and moves along the map. For example when a user goes from point a to point b, the program looks for point b inside the locationList and loads that piece of data with the name point b onto the current Location object. Location class holds the necessary data that is needed for the display of given location. The location name, description, NPCs that are in that location and the list of neighboring locations.

**Player** class type *player* holds the name and the inventory of the current playing player. When an item is acquired the game adds the gotten item to the inventory list. When a player moves or does some kind of action inside the game, they are handled in the **Player** class’ methods:
 - *move()* first checks if the commanded move is legal, e.g. if the new location is in the neighboring list of the current location, and calls the **GameState** to set the location to the new one.
 - *look()* checks if the asked NPC is available in that location and displays information about that NPC, otherwise it states that the NPC is not available.
 -  *talk()* checks if the asked npc is available, and also checks if the user has already obtained the given item, and then shows the riddle of the given npc in order to obtain the item.
 - *say()* is for the user to answer to the given riddle. If the answer is correct the player gets to add the chosen item to their inventory list. To win the game the player needs to collect every item available.

Boolean type *isFinished* is the indicator of whether the gameplay is finished or not. This is used for the main loop of the game as well as for stopping the game when needed without and abruption. Player may choose to manually stop the game by entering the command to quit the game, or in the other case if the game has come to an end.

**InputParser** class is used to parse the user input into *command* and *argument* and return the corresponding measure. It holds all the possible commands as an enum *Command* to check with the entered user input. It splits the user input string into two parts, first being the command and the second being the argument. These inputs then are sterilized and checked for availability. For example if the user inputs a command that is not found, the object stores the *Command* "error" as its command.

**Loader** class is used to take the map json file and parse into the corresponding Location and NPC objects. It consists of static methods:
 - *getHomeLocation()* takes in the *locationList* JSONArray from **GameState** and returns the first location at the 0 index of that list.
 - *getFinalLocation()* accesses the *locationList* of **GameState** instance and gets the last element of JSONArray and returns it. This is the location that where game should end.
 - *parseLocationByName()* this method iterates through the *locationList* of **GameState** and finds the object that has the same name as given in the argument of the method. Otherwise returns null.
 - *allItemsOnMap()* this method is used to check if the user has all the collectable items to finish the game. It returns a list with all the collectable items in the game.
 - *parseMetaData()* this method parses the metadata, such as team name and the game, later UserInterface calls this method to display them. This is done to avoid hardcoding the textual, and team specific information into the classes.

**UserInterface** class is used for graphical user interface. The user first sees an entry page where the game welcomes and asks for a username input. That input is then stored and the game goes on, withing the gama all the information is displayed using the methods of this class. *displayText()* is called to display the given text in the middle output box. To show the NPC-specific images *displayImage()* is called.

**PDFGenerator** class is used to create a certificate when the player finishes the game. The certificate is made using PdfWriter and uses iText to fill up the pdf with content. The stamp for the pdf is saved in the "./src/main/resources/" directory. The created pdf is then saved at the parent directory.



# State Machine Diagrams



Author(s): *Daniel McHugh*





### Feedback from Assignment 2:



The issues with this section in assignment 2 was the inclusion of two state machine diagrams which modelled state-less classes and some confusion with the GameState diagram. Though the latter has been confronted by revising the diagram to a much more simplistic version, the former may still remain an issue as to the reviewer.
The classes found in this project tend to serve either one of two purposes:
- Storing information of the game (Main, Player, Location)
- Storing utility functions for other objects to use (Loader, PDFGenerator, InputParser, DisplayError, UserInterface)

The clear exception to these is the GameState class which has �states� as a more passive quality that is represented by the values of its attributes. However, as a requirement for the assignment, another state machine diagram is to be presented. This shall be the UserInterface object behaviour. The main argument for this decision is that the UserInterface singleton follows a similar development as the GameState singleton as they both loop in parallel while communicating with each other. The loop that the UserInterface performs could be seen as transitioning from one �state� to the other. Therefore, a machine state diagram of the UserInterface object has been included despite the fact that it may qualify as state-less.





## UserInterface behaviour



![state machine diagram](https://i.imgur.com/vsTQNhP.png)



The UserInterface singleton is initiated along with the GameState as the program is started in Main. Within this state, the prep work is done to set up the terminal-like environment the player will be using to interact with the game. This includes setting the terminal window size, setting cursor position and drawing up the image/text/input boxes. While in this state, the object also displays the game title and creator found in the game data files, and returns the given player name on exit. This name is retrieved by the GameState.

Following this stage is the main game loop found also in the GameState behaviour section. The object first enters an internal loop in DisplayInput as the terminal display is updated based on the keystrokes of the user. This includes features which singles the user when attempting faulty input (backspacing with no characters or typing too many characters). This loop completes when the user presses enter with a valid userInput which is then returned to the TakeInput state. TakeInput keeps the user's input and lays idle until it is called within the main loop in Main. There the input is passed on through the input parser to updateGameState.


The transition to the next state Display is called within GameState when the user�s actions result in information needing to be shown to the user. This event is the addPrint in which the UserInterface object must carry a few steps for achieving this. Firstly, on entry, the display screen must be cleared to allow new information to be added. This is done by first clearing the display, then adding basic information which shall always be displayed and then the new information. The inclusion of basic information (Current location/NPCs and neighbouring locations) is a quality-of-life feature which aids users on making their next move. Consequently, the new information called from GameState is displayed.

UserInterface works in parallel to GameState by taking and retrieving information with it inside the main game loop. Therefore, it will repeat the TakeInput and Display states as long as the isFinished condition is not met.



## GameState behaviour



### Version 1



![state machine diagram](https://i.imgur.com/34vOm4p.png)



### Version 2



![state machine diagram](https://i.imgur.com/mitvaKv.png)


The GameState class is initialised when the game is started, requiring information about the players name, the starting location of the player and a map of the whole game. This is achieved by receiving the player�s name from the UserInterface object and loading the rest by parsing the game data files. Moreover, the class has been changed into a singleton. This is for simplicity's sake, as it allows for only one instance of the class to occur, which will be constantly updated based on the actions of the player. The idea to add a saving and loading feature of the GameState was also mentioned in earlier assignments. However, due to time constraints and complexity the feature has been cut. (May still be implemented outside of the assignments)


After initialisation is done, the game enters into its main game loop, which will be the GameLoop state of the object. The GameState object is initialized with false to its isFinished attribute. This will eventually change to true based on the actions taken by the player. On entry, the user�s input retrieved from UserInterface is passed through the inputParser to be sanitised.


In its GameLoop state, the object takes the command and argument(s) from the entry event and passes them to the updateGameState. This may act as a pseudo-state is based on what the command from the user is set as, with the currently available commands being: GO/MOVE, LOOK, TALK, SAY/ANSWER, ERROR, HELP and QUIT. Each of these commands will either update the game state in some way or convey information to the user:



- Update Game Location: updates the location object in the game state based on players movement



- Get Npc Description: retrieves the description of the npc found in the states current location (only when given correct argument)



- Get Npc Riddle: retrieves the riddle from the npc found in the states current location (only when given correct argument)



- Get Npc Item: retrieves an item from the npc found in the states current location (only when given correct argument)



- Input Error: output to the user that the command just passed was not recognised



- Help: output to the user a list of recognised commands and format



- Quit: update the game state to finished




The GO/MOVE and SAY/ANSWER commands will also perform the same tasks. This is to be less restrictive on users input and to accommodate players with different intuitions on how to communicate with the game.



Most of these commands will result in further information which needs to be presented to the user. Therefore, as an exit event, the new information is set back to Userinterface and is handled there. Same as UserInput, GameState will repeat these three steps as long as the isFinished condition is not met along with UserInterface. If the condition is finally met, the GameState enters its final Finished state. It�s only activity is to generate a PDF file and present it to the user which has completed the game as a sort of certificate, displaying some text and a certified approval stamp.



## Sequence diagram

Author: `Sofia Šišić`

![SequenceDiagram1](https://i.imgur.com/LMOnGmD.jpg)
This is a described sequence of events when the playerwant to talk to an NPC and gives input in the form “talk” + NPC’s name. The **UserInterface** class get the input. Then the input is passed to **InputParser** class. In the **InputParser** the input is divided into a *command* and *argument*. The parsed input is passed to **GameState** and the corresponding method is called from the **Player** class. If the command is recognized, the argument is checked for validity. In this case, the argument is NPC’s name. If the inputted name doesn’t correspond with the NPC at that location, the error is being printed saying “NPC is not here” and the player can try again. However, if the NPC’s name is correct, another check is being done, and that is whether the player has the item already (each item is associated with only one NPC and only one location) in their inventory. If so, the text stored in the JSON file under “returnDialog” is outputted. If not, the riddle associated with the NPC is displayed.

![SequenceDiagram1](https://i.imgur.com/TZqKf9m.jpg[/img])

The second sequence diagram shows the sequence of events when the player wants to have a closer look at the NPC. The player can do so by typing "look" + NPC's name. The beginning of this process is very similar to the one described above. The **UserInterface** class get the input. Then the input is passed to **InputParser** class. In the **InputParser** the input is divided into a *command* and *argument*. The parsed input is passed to **GameState** and the corresponding method is called from the **Player** class. If the command is recognized, the argument is checked for validity. In this case, the argument is NPC’s name. If the inputted name doesn’t correspond with the NPC at that location, the error is being printed saying “NPC is not here” and the player can try again. However, if the NPC’s name is correct, user interface display's the image corresponding to that character. At the same time the "about" ??? from the JSON file is printed and the player is provided with the additional information.
