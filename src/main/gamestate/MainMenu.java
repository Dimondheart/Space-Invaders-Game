package main.gamestate;

import java.awt.Color;
import java.awt.Image;

import main.gfx.Gfx;
import main.inputdevice.InputManager;

import static java.awt.event.KeyEvent.*;
import static main.inputdevice.MouseInput.*;

/** The main menu, which appears first on game startup. */
public class MainMenu extends GameState
{
	@Override
	public synchronized void cycleState()
	{
		// Dummy implementation of transitioning to a level
		if (InputManager.getMouse().isBtnClicked(LEFT_BUTTON))
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
		Gfx.loadGraphic("mainmenu/TestGraphic.png");
	}
	
	@Override
	protected synchronized void renderState()
	{
		// Dummy implementation of rendering
		Gfx.clearAllLayers();
		Image testG = Gfx.getGraphic("mainmenu/TestGraphic.png");
		layers[0].setColor(Color.yellow);
		layers[0].fillRect(
				0,
				0,
				Gfx.getLayerWidth(0),
				Gfx.getLayerHeight(0)
				);
		layers[5].setColor(Color.blue);
		layers[5].drawImage(
				testG,
				Gfx.getLayerWidth(5)/4,
				Gfx.getLayerHeight(5)/4,
				null
				);
	}
}
