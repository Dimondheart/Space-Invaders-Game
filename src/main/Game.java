package main;

import main.gamestate.*;

/** Represents a game session, handling initialization and general game events
 * or actions
 */
public class Game
{
	// The object used for thread timing and tick handling
	private ThreadClock clock;
	
	// Current state of the game
	private GameState gameState;
	
	/** Default constructor, creates instances of important objects. */
	public Game()
	{
		// Create the thread clock w/ default priority
		clock = new ThreadClock();
	}
	
	/** Initializes core components then calls the run function. */
	public void start()
	{
		gameState = new MainMenu();
		run();
	}
	
	/** Calls functions for handling game states and game state transitions. */
	private void run()
	{
		while (true)
		{
			// Setup for this game state
			gameState.setup();
			// Loop until the current state should be transitioned
			while (!gameState.changeStateIndicated())
			{
				// Call one cycle of events for this game state
				gameState.cycle();
				// Next thread tick
				clock.nextTick();
			}
			// Cleanup operations for this game state
			gameState.cleanup();
			// Gets the new object for the state
			gameState = makeNewGameState(gameState.getNewState());
			// Check if the new state is one of the quit program states
			if (gameState == null)
			{
				quit();
			}
		}
	}
	
	/** Creates the new game state object.
	 * @return - (GameState) The new game state object
	 * <p>OR
	 * <p>- null for states that indicate the program should terminate
	 */
	private GameState makeNewGameState(GameState.GameStates stateName)
	{
		switch (stateName)
		{
		// Contains things like options and loading/starting a level
		case MAIN_MENU:
			return new MainMenu();
			
		// When a level has been loaded 
		case PLAY_LEVEL:
			return new PlayLevel();
		
		// Quit is a game state that indicates program should be terminated
		case QUIT:
			return null;
			
		// Default to the main menu
		default:
			return new MainMenu();
		}
	}
	
	/** Does or calls cleanup  operations then terminates the program. */
	private void quit()
	{
		System.out.println("Quitting");
		System.exit(0);
	}
}
