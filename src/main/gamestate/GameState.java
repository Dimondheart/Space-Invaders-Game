package main.gamestate;

/** Base Class for a game state. Import this as an interface for all
 * the game states.
 */
public abstract class GameState
{
	/** Indicates when to transition out of this class. */
	public boolean changeState = false;
	/** Specifies the type of the game state to transition to next. */
	public GameStates newState = GameStates.MAIN_MENU;
	/** The different states the game can be in. */
	public enum GameStates
	{
		MAIN_MENU,
		PLAY_LEVEL,
		QUIT
	}
	
	/** Setup stuff for a state. */
	public abstract void setup();
	/** One processing cycle of actions for a game state. */
	public abstract void cycle();
	/** Stuff to clean up when transitioning out of a game state. */
	public abstract void cleanup();
	/** Renders all graphics for a game state. */
	public abstract void render();
	
	/** Checks if the game state should be transitioned to another state.
	 * @return True if the state should be transitioned
	 */
	public boolean changeStateIndicated()
	{
		return changeState;
	}
	
	/** Gets the next state indicated by the game state.
	 * @return (GameStates) The new state
	 */
	public GameStates getNewState()
	{
		return newState;
	}
}
