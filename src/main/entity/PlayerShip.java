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
		type = EntityType.PLAYER;
		body = new Body(20);
		health = new Health(3);
		weapon = new Weapon();
	}

	@Override
	public void renderEntity(Graphics2D g2)
	{
		// Get the center x
		int x = body.getX();
		// Get the center y and adjust it to screen coordinates
		int y = Gfx.getFrameHeight() - body.getY();
		// Change x and y to the upper left corner
		x = x - body.getRadius();
		y = y - body.getRadius();
		g2.setColor(Color.blue);
		// Draw a placeholder graphic
		g2.fillOval(x, y, body.getRadius()*2, body.getRadius()*2);
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
