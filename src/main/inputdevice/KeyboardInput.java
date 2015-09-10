package main.inputdevice;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

// TODO?: Rewrite this to work as an event dispatcher
/** Handles processing of keyboard events. */
public class KeyboardInput extends InputDevice implements KeyListener
{
	// Number of key values to be used
	private static final int KEY_COUNT = 256;
	
	// States each key can be in
	private enum KeyState
	{
		RELEASED,  // Not down
		PRESSED,  // Down, but not first pressed this frame
		ONCE  // Down for the first time
	}
	
	// Current state of the keyboard
	private boolean[] currentKeys = null;
	
	// Polled keyboard state
	private KeyState[] keys = null;
	
	/** Constructor, takes a reference to the frame it adds itself to. */
	public KeyboardInput(JFrame frame)
	{
		frame.addKeyListener(this);
		clear();
	}
	
	@Override
	public synchronized void clear()
	{
		// current pressed/released state of keys
		currentKeys = new boolean[KEY_COUNT];
		// Key state beyond just pressed/released
		keys = new KeyState[KEY_COUNT];
		// Set all keys as released
		for (int i = 0; i < KEY_COUNT; ++i)
		{
			keys[i] = KeyState.RELEASED;
		}
	}
	
	@Override
	public synchronized void poll()
	{
		// For all used key IDs
		for (int i = 0; i < KEY_COUNT; ++i)
		{
			// Set the key state if it has been pressed
			if (currentKeys[i])
			{
				// If key is down not but not the previous frame, set to once
				if (keys[i] == KeyState.RELEASED)
				{
					keys[i] = KeyState.ONCE;
				}
				// Otherwise set to pressed
				else
				{
					keys[i] = KeyState.PRESSED;
				}
			}
			// Otherwise set the key state to released
			else
			{
				keys[i] = KeyState.RELEASED;
			}
		}
	}
	
	/** Checks if the specified key is pressed down.
	 * @return True if the key is pressed
	 */
	public boolean isKeyDown(int keyCode)
	{
		return (
				isKeyDownOnce(keyCode) ||
				keys[keyCode] == KeyState.PRESSED
				);
	}
	
	/** Checks if the specified key is pressed down for the first time
	 * since the last poll.
	 * @return True if the key was first pressed during the last poll.
	 */
	public boolean isKeyDownOnce(int keyCode)
	{
		return (keys[keyCode] == KeyState.ONCE);
	}
	
	@Override
	public synchronized void keyPressed(KeyEvent e)
	{
		// Get the key's integer ID
		int keyCode = e.getKeyCode();
		// Check if key is in range of used keys
		if (keyCode >= 0 && keyCode < KEY_COUNT)
		{
			// Current key set to pressed
			currentKeys[keyCode] = true;
		}
	}
	
	@Override
	public synchronized void keyReleased(KeyEvent e)
	{
		// Get the key's integer ID
		int keyCode = e.getKeyCode();
		// Check if key in range of used keys
		if (keyCode >= 0 && keyCode < KEY_COUNT)
		{
			// Set key as released
			currentKeys[keyCode] = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		// Not used
	}
}
