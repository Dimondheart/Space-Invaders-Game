package main.gamestate;

import java.awt.Graphics2D;

import main.gfx.Gfx;
import main.inputdevice.InputManager;

import static java.awt.event.KeyEvent.*;

/** Base Class for a game state. Import this as an interface for all
 * the game states.
 */
public abstract class GameState
{
	private static boolean quitIndicated = false;
	private static boolean suspendIndicated = false;
	/** Indicates when to transition out of the current state. */
	public boolean stateChange = false;
	/** Specifies the type of the game state to transition to next. */
	public GameStates newState = GameStates.MAIN_MENU;
	/** List of the drawing surfaces for each layer, in order
	 * (0 = lowest layer).
	 * <br> TODO: Make a separate list for background, main, and GUI layers.
	 */
	protected Graphics2D[] layers = new Graphics2D[main.gfx.Gfx.NUM_LAYERS];
	
	/** The different types of states the game can be in. */
	public enum GameStates
	{
		MAIN_MENU,
		PLAY_LEVEL,
		QUIT
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
	
	/** Indicates that the game state should be transitioned to
	 * a quit state.  Does not take effect until the start of the
	 * next call to <tt>cycle()</tt>.
	 */
	public static synchronized void indicateQuit()
	{
		quitIndicated = true;
	}
	
	/** Indicate that the current game state should be paused because of
	 * certain events, like minimizing the game window.
	 */
	public static synchronized void indicateSuspend()
	{
		suspendIndicated = true;
	}
	
	/** Resets the variable indicating that a state should suspend. */
	private static synchronized void resetSuspend()
	{
		suspendIndicated = false;
	}
	
	/** Does any changes when the state should suspend in response to events,
	 * like minimizing the window. Subclasses should override this if they
	 * want to respond to these events; by default it does nothing.
	 */
	protected void suspend()
	{
		// Do nothing by default
	}
	
	/** One processing cycle of actions for a game state. */
	public void cycle()
	{
		// Activate/deactivate debug mode
		if (InputManager.getKeyboard().isKeyDownOnce(VK_F3))
		{
			main.Game.toggleDebug();
		}
		// Check if a quit status has been indicated
		if (quitIndicated)
		{
			changeState(GameStates.QUIT);
			return;
		}
		if (suspendIndicated)
		{
			suspend();
			resetSuspend();
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
		if (quitIndicated || (isChangeStateIndicated() && newState == GameStates.QUIT))
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
}
