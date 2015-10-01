package main.gamestate.level;

import java.awt.Color;
import java.awt.Graphics2D;

import main.entity.Barrier;
import main.entity.BasicEnemyShip;
import main.entity.EntityManager;
import main.entity.PlayerShip;
import main.gfx.Gfx;

/** Represents a single level.  A level will only reach victory once, meaning
 * if you make 3 stages to play each stage would be a separate level.
 */
public class Level
{
	private static int width = 400;
	private static int height = 400;
	private boolean paused;
	private Color pauseBGColor;
	private Graphics2D[] guiLayers;
	private EntityManager entityManager;
	private LevelState state;
	
	/** Different states or stages the level can be in. */
	public enum LevelState
	{
		VICTORY,
		IN_PROGRESS,
		GAME_OVER
	}
	
	/** Can specify a level to start with. */
	public Level(int level)
	{
		state = LevelState.IN_PROGRESS;
		resume();
		guiLayers = new Graphics2D[2];
		pauseBGColor = new Color(128,128,128,128);
		entityManager = new EntityManager();
		// Create the player
		entityManager.addPlayer(new PlayerShip());
		setupLevel(level);
	}
	
	/** Gets if the level is paused or not. */
	public synchronized boolean isPaused()
	{
		return paused;
	}
	
	/** Gets the current LevelState state of the level. */
	public LevelState getLevelState()
	{
		return state;
	}
	
	public static int getLevelWidth()
	{
		return width;
	}
	
	public static int getLevelHeight()
	{
		return height;
	}
	
	/** Set the state of the level to pause. */
	public synchronized void pause()
	{
		paused = true;
	}
	
	/** Set the level state to resume. */
	public synchronized void resume()
	{
		paused = false;
	}
	
	/** Inverts the pause state of the level. */
	public synchronized void togglePause()
	{
		/* Implemented this instead of just paused = !paused to prevent
		 * ripple effects of code changes.
		 */
		if (isPaused())
		{
			resume();
		}
		else
		{
			pause();
		}
	}
	
	/** Updates the level. */
	public synchronized void update()
	{
		entityManager.updateEntities();
		// Basic victory condition
		if (entityManager.numEnemiesLeft() <= 0)
		{
			state = LevelState.VICTORY;
		}
		// Basic game over condition
		else if (entityManager.numPlayersLeft() <= 0)
		{
			state = LevelState.GAME_OVER;
		}
	}
	
	/** Cleans up various resources, like static stuff, used by a level. */
	public void reset()
	{
		EntityManager.reset();
	}
	
	/** Renders entities and any other level-specific stuff. */
	public synchronized void render()
	{
		guiLayers[0] = Gfx.getLayerSurface(5);
		guiLayers[1] = Gfx.getLayerSurface(6);
		entityManager.renderAll();
		// Draw pause related stuff
		if (isPaused())
		{
			
			guiLayers[0].setColor(pauseBGColor);
			guiLayers[0].fillRect(0, 0, Gfx.getFrameWidth(), Gfx.getFrameHeight());
			guiLayers[1].drawImage(
					Gfx.getScaledGraphic(
							"pausemenu/TestGraphic.png",
							Gfx.getLayerScaleFactor(5),
							Gfx.getLayerScaleFactor(5)
							),
					Gfx.getLayerWidth(5)/4,
					Gfx.getLayerHeight(5)/4,
					null
					);
		}
	}
	
	/** TODO: Find a better way to store hand made levels. */
	private void setupLevel(int level)
	{
		switch (level)
		{
			// Simple debugging level
			case 0:
				entityManager.addEnemy(new BasicEnemyShip(200, 400-20-4));
				break;
				
			case 1:
				// Create test barriers
				for (int x = 200-8*3; x < 200+8*3; x += 8)
				{
					entityManager.addAmbient(new Barrier(x, 76));
				}
				// Test enemies
				entityManager.addEnemy(new BasicEnemyShip(150, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(175, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(225, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(250, 400-20-4));
				break;
				
			case 2:
				// Create test barriers
				for (int x = 200-8*3; x < 200+8*3; x += 8)
				{
					entityManager.addAmbient(new Barrier(x, 100));
					entityManager.addAmbient(new Barrier(x, 92));
					entityManager.addAmbient(new Barrier(x, 84));
					entityManager.addAmbient(new Barrier(x, 76));
				}
				// Test enemies
				entityManager.addEnemy(new BasicEnemyShip(150, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(175, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(200, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(225, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(250, 400-20-4));
				entityManager.addEnemy(new BasicEnemyShip(175, 400-40-4));
				entityManager.addEnemy(new BasicEnemyShip(200, 400-40-4));
				entityManager.addEnemy(new BasicEnemyShip(225, 400-40-4));
				break;
				
			default:
				break;
		}
	}
}
