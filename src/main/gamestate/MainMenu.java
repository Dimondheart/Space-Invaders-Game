package main.gamestate;

import java.awt.Color;

import main.gfx.Gfx;

/** The main menu, which appears first on game startup. */
public class MainMenu extends GameState
{
	private int counter = 0;

	@Override
	public synchronized void cycle()
	{
		// TODO: Implement functionality
		System.out.println("In Main Menu");
		// Dummy implementation of transitioning to a level
		++counter;
		if (counter > 50)
		{
			newState = GameStates.PLAY_LEVEL;
			changeState = true;
		}
	}

	@Override
	public synchronized void cleanup()
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	protected synchronized void initialize()
	{
		// TODO: Implement setup
	}
	
	@Override
	protected synchronized void renderState()
	{
		// TODO: Implement 'actual' rendering
		// Dummy implementation of rendering
		Gfx.clearAllLayers();
		layers[0].setColor(Color.yellow);
		layers[0].fillRect(
				0,
				0,
				Gfx.getFrameWidth(),
				Gfx.getFrameHeight()
				);
		layers[1].setColor(Color.blue);
		layers[1].fillRect(
				0,
				0,
				Gfx.getFrameWidth()/2,
				Gfx.getFrameHeight()/2
				);
	}
}
