package main.gamestate;

import java.awt.Color;

import main.gfx.Gfx;

/** Game state when a level has been loaded and should be started. */
public class PlayLevel extends GameState
{
	private boolean renderBG = true;
	private int counter = 0;
	
	// TODO: Create a constructor for specifying the starting level
	
	@Override
	protected synchronized void initialize()
	{
		// TODO: Add setup stuff
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
		int[] layersToClear = {1,2};
		Gfx.clearLayers(layersToClear);
		// Test with being able to render a layer once without clearing it
		if (renderBG)
		{
			System.out.println("RenderingBG");
			renderBG = false;
			layers[0].setColor(Color.cyan);
			layers[0].fillRect(
					0,
					0,
					Gfx.getFrameWidth(),
					Gfx.getFrameHeight()
					);
		}
		layers[1].setColor(Color.blue);
		layers[2].setColor(Color.yellow);
		// Test of drawing to higher layer first
		layers[2].drawRect(
				Gfx.getFrameWidth()/4+5,
				Gfx.getFrameHeight()/4+5,
				Gfx.getFrameWidth()/2-10,
				Gfx.getFrameHeight()/2-10
				);
		layers[1].fillRect(
				Gfx.getFrameWidth()/4,
				Gfx.getFrameHeight()/4,
				Gfx.getFrameWidth()/2,
				Gfx.getFrameHeight()/2
				);
	}
}
