package main.gamestate;

/** The main menu, which appears first on game startup. */
public class MainMenu extends GameState
{
	@Override
	public void setup()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void cycle()
	{
		// TODO: Implement functionality
		System.out.println("In Main Menu");
		// Dummy implementation of transitioning to a level
		newState = GameStates.PLAY_LEVEL;
		changeState = true;
	}

	@Override
	public void cleanup()
	{
		// TODO Auto-generated method stub
	}
}
