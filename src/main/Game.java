package main;

import main.gamestate.*;

/** Represents a game session, handling initialization and general game events
 * or actions.
 * <br>Is threaded, but uses the Main thread instead of creating its own
 * thread.
 */
public class Game
{
	/** Allows/denies certain debugging operations. */
	public static boolean debug_mode = false;
	/** The clock/cycle controller for this thread. */
	private ThreadClock clock;
	/** Current state of the game. */
	private static GameState gameState;
	/** The primary graphics handling object. */
	private main.gfx.Gfx graphics;
	/** The sound handling system. */
	private main.SoundSystem soundSys;
	
	/** Default constructor, creates instances of important objects. */
	public Game()
	{
		// Create the thread clock w/ default priority
		clock = new ThreadClock();
		// Setup the graphics system
		graphics = new main.gfx.Gfx();
		// Setup the sound system
		soundSys = new main.SoundSystem();
		// Select a game state to start with
		createNewGameState(GameState.GameStates.MAIN_MENU);
	}
	
	/** Toggles the debug mode variable. */
	public static synchronized void toggleDebug()
	{
		debug_mode = !debug_mode;
	}
	
	/** If debug mode is enabled. */
	public static synchronized boolean debugEnabled()
	{
		return debug_mode;
	}
	
	/** Initializes core components then calls the run function. */
	public void start()
	{
		// Start all other threaded systems/objects
		graphics.start();
		soundSys.start();
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
			while (!gameState.isChangeStateIndicated())
			{
				// Call one cycle of events for this game state
				gameState.cycle();
				// Next thread tick
				clock.nextTick();
			}
			// Cleanup operations for this game state
			gameState.cleanup();
			// Set the new game state
			createNewGameState(gameState.getNewState());
		}
	}
	
	/** Creates the new game state object.
	 * @return - (GameState) The new game state object
	 * <p>OR
	 * <p>- null for states that indicate the program should terminate
	 */
	private void createNewGameState(GameState.GameStates stateName)
	{
		switch (stateName)
		{
		// The classic space invaders mode
		case PLAY_LEVEL:
			gameState = new ClassicMode();
			break;
		
		// Quit is a game state that indicates program should be terminated
		case QUIT:
			gameState = new QuitGame();
			gameState.cleanup();
			quit();
			break;
			
		// Contains things like options and loading/starting a level
		case MAIN_MENU:
			// Fall through
			
		// Default to the main menu
		default:
			gameState = new MainMenu();
		}
	}
	
	/** Does or calls cleanup operations then terminates the program. */
	private void quit()
	{
		System.exit(0);
	}
}
