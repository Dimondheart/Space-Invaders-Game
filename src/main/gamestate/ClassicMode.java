package main.gamestate;

import java.awt.Color;
import java.util.Random;

import main.gfx.Gfx;
import main.inputdevice.InputManager;
import main.entity.*;
import main.gamestate.level.Level;
import main.gamestate.level.Level.LevelState;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;

/** Game state when a level has been loaded and should be started. */
public class ClassicMode extends GameState
{
	private boolean renderBG;
	private int levelNum;
	private Level level;
	
	// TODO?: Create a constructor for specifying the starting level
	
	@Override
	protected synchronized void initialize()
	{
		renderBG = true;
		levelNum = 0;
		level = new Level(levelNum);
	}

	@Override
	public synchronized void cycleState()
	{
		// Test game over
		if (level.getLevelState() == LevelState.GAME_OVER)
		{
			System.out.println("Restarting");
			levelNum = 0;
			level = new Level(levelNum);
		}
		// Test simple level transition
		else if (level.getLevelState() == LevelState.VICTORY)
		{
			++levelNum;
			if (levelNum > 2)
			{
				changeState(GameStates.MAIN_MENU);
			}
			else
			{
				System.out.println("Starting Level " + levelNum);
				level = new Level(levelNum);
			}
		}
		// Pause the level
		if (InputManager.getKeyboard().isKeyDownOnce(VK_ESCAPE))
		{
			level.togglePause();
		}
		// Dummy transition to quitting the game
		if (level.isPaused() && InputManager.getMouse().isBtnClicked(BUTTON1))
		{
			changeState(GameStates.MAIN_MENU);
		}
		// Update the entities unless paused
		if (!level.isPaused())
		{
			level.update();
		}
	}

	@Override
	public synchronized void cleanup()
	{
		level.reset();
	}

	@Override
	protected synchronized void renderState()
	{
		// TODO: Fix issue w/ having to resize once before entities  will draw
		// Clear specified layers
		Gfx.clearLayersInRange(2, 6);
		// Render the background, if not already rendered once
		if (renderBG)
		{
			Gfx.clearLayersInRange(0, 1);
			renderBG = false;
			drawBG();
		}
		// Render the level stuff
		level.render();
	}
	
	@Override
	protected synchronized void suspend()
	{
		level.pause();
	}
	
	/** Draw the background graphics. */
	private void drawBG()
	{
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
			int x = rng.nextInt(Gfx.getFrameWidth()-4);
			int y = rng.nextInt(Gfx.getFrameHeight()-4);
			int radius = rng.nextInt(4);
			if (radius == 0)
			{
				radius = 1;
			}
			layers[1].fillOval(x, y, radius, radius);
		}
	}
}
