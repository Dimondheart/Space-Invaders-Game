package main.gamestate;

import java.awt.Graphics2D;

import main.gfx.Gfx;
import main.inputdevice.InputManager;

/** Base Class for a game state. Import this as an interface for all
 * the game states.
 */
public abstract class GameState
{
	private static boolean quitIndicated = false;
	/** Indicates when to transition out of the current state. */
	public boolean stateChange = false;
	/** Specifies the type of the game state to transition to next. */
	public GameStates newState = GameStates.MAIN_MENU;
	/** List of the drawing surfaces for each layer, in order
	 * (0 = lowest layer).
	 */
	protected Graphics2D[] layers = new Graphics2D[main.gfx.Gfx.NUM_LAYERS];
	/** The manager of all user inputs. */
	protected InputManager inputs;
	
	/** The different types of states the game can be in. */
	public enum GameStates
	{
		MAIN_MENU,
		PLAY_LEVEL,
		QUIT
	}
	
	public GameState()
	{
		// Create input manager
		inputs = new InputManager();
	}
	
	/** State-specific processing operations. */
	public abstract void cycleState();
	
	/** Stuff to clean up when transitioning out of a game state.
	 * Normally should be called after a change state has been indicated,
	 * but before transitioning to the new game state.
	 * In the see also section below are exceptions to this pattern.
	 * @see QuitGame
	 */
	public abstract void cleanup();
	
	/** Setup operations specific to each game state. */
	protected abstract void initialize();
	
	/** Renders all graphics for a game state.  This is different than
	 * <tt>render()</tt>, which performs generic setup operations
	 * to help a state render properly. */
	protected abstract void renderState();
	
	/** Checks if the game state should be transitioned to another state.
	 * @return True if the state should be transitioned
	 */
	public boolean isChangeStateIndicated()
	{
		return stateChange;
	}
	
	/** Gets the type of the new state that should be transitioned to next.
	 * @return (GameStates) The new state
	 */
	public GameStates getNewState()
	{
		return newState;
	}
	
	/** One processing cycle of actions for a game state. */
	public void cycle()
	{
		// Check if a quit status has been indicated
		if (quitIndicated)
		{
			changeState(GameStates.QUIT);
			return;
		}
		// Update input devices
		InputManager.updateAllInputs();
		cycleState();
	}
	
	/** Performs setup operations for this game state. Should be called once
	 * at the start of a new game state.
	 */
	public void setup()
	{
		// Update the reference to this object in graphics
		main.gfx.Gfx.updateGameState(this);
		// Clear all the layers
		Gfx.clearAllLayers();
		// Calls the state-specific setup function
		initialize();
	}
	
	/** Renders the current game state. */
	public void render()
	{
		// Don't bother rendering if quit is indicated or moving into quit
		if (quitIndicated || (stateChange && newState == GameStates.QUIT))
		{
			return;
		}
		// Update the list of drawing surfaces for the layers
		for (int i = 0; i < main.gfx.Gfx.NUM_LAYERS; ++i)
		{
			layers[i] = main.gfx.Gfx.getLayerSurface(i);
		}
		// Call the state-specific rendering function
		renderState();
	}
	
	/** Indicates that a state transition is requested, and specifys
	 * the type of the new state.
	 * @param newState (GameStates) the new state's type
	 */
	protected synchronized void changeState(GameStates newState)
	{
		this.newState = newState;
		stateChange = true;
	}
	
	/** Indicates that the game state should be transitioned to
	 * a quit state.  Does not take effect until the start of the
	 * next call to <tt>cycle()</tt>.
	 */
	public static synchronized void indicateQuit()
	{
		quitIndicated = true;
	}
}
