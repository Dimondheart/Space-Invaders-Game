package main.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.gfx.Gfx;
import main.inputdevice.InputManager;
import main.gamestate.level.Level;
import main.gamestate.level.Level.LevelState;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;

/** Game state when a level has been loaded and should be started. */
public class ClassicMode extends GameState
{
	private int levelNum;
	private Level level;
	private BufferedImage bgImg;
	// Color used to highlight the 'battleground'
	private Color highlightColor = new Color(200,200,200,50);
	
	// TODO?: Create a constructor for specifying the starting level
	
	@Override
	protected synchronized void initialize()
	{
		levelNum = 0;
		level = new Level(levelNum);
		// Draw the second layer of the background
		drawBGSpecial();
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
				System.out.println("Starting Level: " + levelNum);
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
		Gfx.clearAllLayers();
		drawBG();
		// Render the level stuff
		level.render();
		// Render the stuff that highlights the 'battlefield'
		layers[5].setColor(highlightColor);
		// Add highlight to the sides
		if(Gfx.getFrameWidth() > Gfx.getFrameHeight())
		{
			int width = Gfx.getLayerWidth(0)-Gfx.getLayerWidth(2);
			int height = Gfx.getLayerHeight(5);
			layers[5].fillRect(
					Gfx.getLayerWidth(2),
					0,
					width,
					height
					);
		}
		// Add horizontal highlights
		else if(Gfx.getFrameWidth() < Gfx.getFrameHeight())
		{
			int width = Gfx.getLayerWidth(0);
			int height = Gfx.getLayerHeight(0)-Gfx.getLayerHeight(2);
			layers[5].fillRect(
					0,
					Gfx.getLayerHeight(2),
					width,
					height
					);
		}
	}
	
	@Override
	protected synchronized void suspend()
	{
		level.pause();
	}
	
	/** Draw the background graphics. */
	private void drawBG()
	{
		// Draw main background (solid color)
		layers[0].setColor(Color.black);
		layers[0].fillRect(
				0,
				0,
				Gfx.getFrameWidth(),
				Gfx.getFrameHeight()
				);
		// Draw the special "foreground-background"
		layers[1].drawImage(bgImg, 0, 0, bgImg.getWidth(), bgImg.getHeight(), null);
	}
	
	/** Renders the special front part of the background. */
	private void drawBGSpecial()
	{
		Rectangle BGSize;
		// Get the max possible screen dimensions
		BGSize=GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration()
				.getBounds();
		// Create buffered image
		bgImg = new BufferedImage(
				BGSize.width,
				BGSize.height,
				BufferedImage.TYPE_INT_ARGB
				);
		Graphics2D surf = bgImg.createGraphics();
		Random rng = new Random();
		/* Determine the number of stars, based on the area of the BG graphic.
		 * Note: The way this is calculated has the same result as
		 * (BG Area) / (Default Window Area), but the below will work with
		 * higher screen resolutions/sizes.
		 */
		int numStars = (int)(
				(BGSize.getWidth()/(double)Gfx.DEFAULT_WINDOW_DIM)
				* (BGSize.getHeight()/(double)Gfx.DEFAULT_WINDOW_DIM)
				* 250.000
				);
		// Draw a number of stars, based on the total area of the graphic
		for (
				int n = 0;
				n < numStars;
				++n
				)
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
				int rand = rng.nextInt(46);
				r = 190;
				g = 190;
				b = 210 + rand;
			}
			// Red/yellow
			else if (primaryColor == 2)
			{
				int rand = rng.nextInt(46);
				r = 210 + rand;
				g = 210 + rand;
				b = 150;
			}
			surf.setColor(new Color(r,g,b,a));
			int x = rng.nextInt(BGSize.width-4);
			int y = rng.nextInt(BGSize.height-4);
			int radius = rng.nextInt(4);
			if (radius == 0)
			{
				radius = 1;
			}
			surf.fillOval(x, y, radius, radius);
		}
	}
}
