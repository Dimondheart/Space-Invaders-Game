package main.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import main.gfx.Gfx;

/** Game state when a level has been loaded and should be started. */
public class PlayLevel extends GameState
{
	private int counter = 0;
	
	// TODO: Create a default constructor that loads level 1
	// TODO?: Create a constructor for specifying a different starting level
	
	@Override
	public synchronized void setup()
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
	public synchronized void render()
	{
		Graphics2D bottomLayer = Gfx.getLayerSurface(0);
		Graphics2D layer1 = Gfx.getLayerSurface(1);
		layer1.setColor(Color.cyan);
		// Yes, I am having fun drawing on a higher layer first :3
		layer1.fillRect(
				Gfx.getRenderAreaWidth()/4,
				0,
				Gfx.getRenderAreaWidth()/2,
				Gfx.getRenderAreaHeight()
				);
		bottomLayer.setColor(Color.black);
		bottomLayer.fillRect(0, 0, Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight());
	}
}
