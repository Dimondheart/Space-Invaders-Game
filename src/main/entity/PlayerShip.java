package main.entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import main.entity.entitycomponent.*;
import main.gfx.Gfx;
import main.inputdevice.InputManager;

import static java.awt.event.KeyEvent.*;

/** The player controlled space ship. */
public class PlayerShip extends Entity
{
	/** Basic constructor. */
	public PlayerShip()
	{
		renderColor = Color.blue;
		type = EntityType.PLAYER;
		body = new Body(new Dimension(19,25));
		health = new Health(3);
		weapon = new Weapon();
	}

	@Override
	public void loadGraphics()
	{
		Gfx.loadGraphic("entities/playership/basic.png");
	}
	
	@Override
	public void update()
	{
		int directionX = 0;
		int directionY = 0;
		if (InputManager.getKeyboard().isKeyDown(VK_A))
		{
			--directionX;
		}
		if (InputManager.getKeyboard().isKeyDown(VK_D))
		{
			++directionX;
		}
		// Debug mode - free movement
		if (main.Game.debugEnabled())
		{
			if (InputManager.getKeyboard().isKeyDown(VK_S))
			{
				--directionY;
			}
			if (InputManager.getKeyboard().isKeyDown(VK_W))
			{
				++directionY;
			}
		}
		body.setVector(directionX*4, directionY*4);
		body.move();
		if (InputManager.getKeyboard().isKeyDown(VK_SPACE))
		{
			weapon.fire(this);
		}
	}
	
	@Override
	public void renderEntity(Graphics2D g2)
	{
		g2.setColor(renderColor);
		g2.drawImage(
				Gfx.getScaledGraphic(
						"entities/playership/basic.png",
						Gfx.getLayerScaleFactor(3),
						Gfx.getLayerScaleFactor(3)
						),
				scrX,
				scrY,
				null
				);
	}
}
