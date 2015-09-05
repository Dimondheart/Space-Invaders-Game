package main.gamestate;

/** Game state when a level has been loaded and should be started. */
public class PlayLevel extends GameState
{
	// TODO: Create a default constructor that loads level 1
	// TODO?: Create a constructor for specifying a different starting level
	
	@Override
	public void setup()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void cycle()
	{
		// TODO: Implement functionality
		System.out.println("In Level");
		// Dummy transition to quitting the game
		newState = GameStates.QUIT;
		changeState = true;
	}

	@Override
	public void cleanup()
	{
		// TODO Auto-generated method stub
	}
}
