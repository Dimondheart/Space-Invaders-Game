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
	private EntityManager entityManager;
	
	// TODO: Create a constructor for specifying the starting level
	
	@Override
	protected synchronized void initialize()
	{
		renderBG = true;
		paused = false;
		pauseBGColor = new Color(128,128,128,128);
		// Create the entity manager
		entityManager = new EntityManager();
	}

	@Override
	public synchronized void cycleState()
	{
		// Pause the level
		if (InputManager.getKeyboard().isKeyDownOnce(VK_ESCAPE))
		{
			paused = !paused;
		}
		// Dummy transition to quitting the game
		if (paused && InputManager.getMouse().isBtnClicked(BUTTON1))
		{
			changeState(GameStates.MAIN_MENU);
		}
		// Update the entities unless paused
		if (!paused)
		{
			entityManager.updateEntities();
		}
	}

	@Override
	public synchronized void cleanup()
	{
		EntityManager.reset();
	}

	@Override
	protected synchronized void renderState()
	{
		int[] layersToClear = {1,2,3,5};
		// Clear specified layers
		Gfx.clearLayers(layersToClear);
		// Render the background, if not already rendered once
		if (renderBG)
		{
			renderBG = false;
			layers[0].setColor(Color.black);
			layers[0].fillRect(
					0,
					0,
					Gfx.getFrameWidth(),
					Gfx.getFrameHeight()
					);
		}
		// Render the entities
		entityManager.renderAll(layers[3]);
		// Draw pause related stuff
		if (paused)
		{
			layers[5].setColor(pauseBGColor);
			layers[5].fillRect(0, 0, Gfx.getFrameWidth(), Gfx.getFrameHeight());
		}
	}
}
