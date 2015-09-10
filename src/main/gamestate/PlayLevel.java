package main.gamestate;

import java.awt.Color;

import main.gfx.Gfx;
import main.inputdevice.InputManager;
import main.entity.*;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;

/** Game state when a level has been loaded and should be started. */
public class PlayLevel extends GameState
{
	private boolean renderBG;
	private boolean paused;
	private Color pauseBGColor;
	private Entity testPlayer;
	
	// TODO: Create a constructor for specifying the starting level
	
	@Override
	protected synchronized void initialize()
	{
		testPlayer = new PlayerShip();
		renderBG = true;
		paused = false;
		pauseBGColor = new Color(128,128,128,128);
	}

	@Override
	public synchronized void cycleState()
	{
		// TODO: Implement functionality
		System.out.println("In Level");
		// Pause the level
		if (InputManager.getKeyboard().isKeyDownOnce(VK_ESCAPE))
		{
			paused = !paused;
		}
		// Dummy transition to quitting the game
		if (paused && InputManager.getMouse().isBtnClicked(BUTTON1))
		{
			changeState(GameStates.QUIT);
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
		int[] layersToClear = {1,2,5};
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
		// Draw pause related stuff
		if (paused)
		{
			layers[5].setColor(pauseBGColor);
			layers[5].fillRect(0, 0, Gfx.getFrameWidth(), Gfx.getFrameHeight());
		}
		// Render the test entity (does not draw to the layer)
		testPlayer.render.render();
	}
}
