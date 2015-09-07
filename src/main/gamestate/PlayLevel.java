package main.gamestate;

import java.awt.Color;

import main.gfx.Gfx;

/** Game state when a level has been loaded and should be started. */
public class PlayLevel extends GameState
{
	private int counter = 0;
	
	// TODO: Create a default constructor that loads level 1
	// TODO?: Create a constructor for specifying a different starting level
	
	@Override
	protected synchronized void initialize()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void cycle()
	{
		// TODO: Implement functionality
		System.out.println("In Level");
		// Dummy transition to quitting the game
		++counter;
		if (counter > 50)
		{
			newState = GameStates.QUIT;
			changeState = true;
		}
	}

	@Override
	public synchronized void cleanup()
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected synchronized void renderState()
	{
		layers[0].setColor(Color.cyan);
		layers[0].fillRect(
				0,
				0,
				Gfx.getRenderAreaWidth(),
				Gfx.getRenderAreaHeight()
				);
	}
}
