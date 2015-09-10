package main.gamestate;

import java.awt.Color;

import main.gfx.Gfx;
import main.inputdevice.InputManager;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.MouseEvent.*;

/** The main menu, which appears first on game startup. */
public class MainMenu extends GameState
{
	@Override
	public synchronized void cycleState()
	{
		// TODO: Implement functionality
		System.out.println("In Main Menu");
		// Dummy implementation of transitioning to a level
		if (InputManager.getKeyboard().isKeyDownOnce(VK_ENTER))
		{
			changeState(GameStates.PLAY_LEVEL);
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
