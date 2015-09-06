package main.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import main.gfx.Gfx;

/** The main menu, which appears first on game startup. */
public class MainMenu extends GameState
{
	private int counter = 0;
	
	@Override
	public synchronized void setup()
	{
		// TODO: Implement setup
	}

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
	public synchronized void render()
	{
		System.out.println("Got there");
		// TODO: Actually implement drawing and not this dummy implementation
		// Translated: I can't believe this actually worked the first time!
		// TODO: Create a method in GameState/Gfx that gets a spec. # of layer surfaces
		Graphics2D bottomLayer = Gfx.getLayerSurface(0);
		Graphics2D layer2 = Gfx.getLayerSurface(1);
		Graphics2D layer3 = Gfx.getLayerSurface(2);
		layer2.setColor(Color.yellow);
		layer2.fillRect(0, 0, Gfx.getRenderAreaWidth()/2, Gfx.getRenderAreaHeight()/2);
		layer3.setColor(Color.blue);
		layer3.fillRect(0, 0, Gfx.getRenderAreaWidth()/4, Gfx.getRenderAreaHeight()/4);
		bottomLayer.setColor(Color.black);
		bottomLayer.fillRect(0, 0, Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight());
	}
}
