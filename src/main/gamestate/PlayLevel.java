package main.gamestate;

import java.awt.Color;
import java.util.Random;

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
		// Clear specified layers
		Gfx.clearLayersInRange(2, 6);
		// Render the primary background, if not already rendered once
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
			// Generate a random starry background
			Random rng = new Random();
			for (int n = 0; n < 250; ++n)
			{
				int r = 255;
				int g = 255;
				int b = 255;
				int a = 155+rng.nextInt(100);
				int primaryColor = rng.nextInt(3);
				// White
				if (primaryColor == 0)
				{
					r = 255;
					g = 255;
					b = 255;
				}
				// Blue
				else if (primaryColor == 1)
				{
					int rand = rng.nextInt(56);
					r = 190;
					g = 190;
					b = 200 + rand;
				}
				// Red/yellow
				else if (primaryColor == 2)
				{
					int rand = rng.nextInt(56);
					r = 200 + rand;
					g = 200 + rand;
					b = 150;
				}
				layers[1].setColor(new Color(r,g,b,a));
				int x = rng.nextInt(396);
				int y = rng.nextInt(396);
				int radius = rng.nextInt(4);
				if (radius == 0)
				{
					radius = 1;
				}
				layers[1].fillOval(x, y, radius, radius);
			}
		}
		// Render the entities
		entityManager.renderAll();
		// Draw pause related stuff
		if (paused)
		{
			layers[5].setColor(pauseBGColor);
			layers[5].fillRect(0, 0, Gfx.getFrameWidth(), Gfx.getFrameHeight());
		}
	}
}
