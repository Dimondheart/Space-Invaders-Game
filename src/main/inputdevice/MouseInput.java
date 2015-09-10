package main.inputdevice;

import java.awt.event.*;
import javax.swing.JFrame;

//TODO?: Rewrite this to work as an event dispatcher
/** Handles mouse input events. */
public class MouseInput extends InputDevice implements MouseListener, MouseWheelListener, MouseMotionListener
{
	// Number of key values to be used
	private static final int BTN_COUNT = 3;
	
	// States each button can be in
	private enum BtnState
		{
			RELEASED,  // Not down
			PRESSED,  // Down, but not first pressed this frame
			ONCE,  // Down for the first time
			CLICKED  // Button was pressed, then first released this frame
		}
		
	// Current state of the mouse
	private boolean[] currentBtns = null;
	
	// Polled mouse state
	private BtnState[] btns = null;
	
	/** Constructor, takes a reference to the frame it should be added to. */
	public MouseInput(JFrame frame)
	{
		frame.addMouseListener(this);
//		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);
		clear();
	}
	
	@Override
	public synchronized void clear()
	{
		// current pressed/released state of keys
		currentBtns = new boolean[BTN_COUNT];
		// Key state beyond just pressed/released
		btns = new BtnState[BTN_COUNT];
		// Set all keys as released
		for (int i = 0; i < BTN_COUNT; ++i)
		{
			btns[i] = BtnState.RELEASED;
		}
	}
	
	@Override
	public synchronized void poll()
	{
		// For all used button IDs
		for (int i = 0; i < BTN_COUNT; ++i)
		{
			// Set the button state if it has been pressed
			if (currentBtns[i])
			{
				// If key is down not but not the previous frame, set to once
				if (btns[i] == BtnState.RELEASED)
				{
					btns[i] = BtnState.ONCE;
				}
				// Otherwise set to pressed
				else
				{
					btns[i] = BtnState.PRESSED;
				}
			}
			// When the button has been "clicked" (first released)
			else if (btns[i] == BtnState.PRESSED)
			{
				btns[i] = BtnState.CLICKED;
			}
			// Otherwise set the key state to released
			else
			{
				btns[i] = BtnState.RELEASED;
			}
		}
	}
	
	/** Checks if the specified button is pressed down.
	 * @return True if the button is pressed
	 */
	public boolean isBtnDown(int btnCode)
	{
		return (
				isBtnDownOnce(btnCode) ||
				btns[btnCode] == BtnState.PRESSED
				);
	}
	
	/** Checks if the specified button is pressed down for the first time
	 * since the last poll.
	 * @return True if the button was first pressed during the last poll.
	 */
	public boolean isBtnDownOnce(int btnCode)
	{
		return (btns[btnCode] == BtnState.ONCE);
	}
	
	/** Checks if the specified button was clicked (pressed and released)
	 * and the release was during the last poll.
	 * @return True if the button was released first during the last poll
	 */
	public boolean isBtnClicked(int btnCode)
	{
		return (btns[btnCode] == BtnState.CLICKED);
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		// Get the btn's integer ID
		int btnCode = e.getButton();
		// Check if btn is in range of used btns
		if (btnCode >= 0 && btnCode < BTN_COUNT)
		{
			// Current btn set to pressed
			currentBtns[btnCode] = true;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Get the btn's integer ID
		int btnCode = e.getButton();
		// Check if btn is in range of used btns
		if (btnCode >= 0 && btnCode < BTN_COUNT)
		{
			// Current btn set to released
			currentBtns[btnCode] = false;
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
	}
}
