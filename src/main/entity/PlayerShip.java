package main.entity;

import java.awt.Color;
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
		body = new Body(20);
		health = new Health(3);
		weapon = new Weapon();
	}

	@Override
	public void update()
	{
		int direction = 0;
		if (InputManager.getKeyboard().isKeyDown(VK_A))
		{
			--direction;
		}
		if (InputManager.getKeyboard().isKeyDown(VK_D))
		{
			++direction;
		}
		body.setVector(direction*4, 0);
		body.move();
		if (InputManager.getKeyboard().isKeyDown(VK_SPACE))
		{
			weapon.fire(body, type);
		}
	}
}
