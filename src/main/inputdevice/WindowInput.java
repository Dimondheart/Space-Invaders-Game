package main.inputdevice;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

import main.gamestate.GameState;

public class WindowInput extends InputDevice implements WindowListener
{
	/** Constructor which takes a reference to the frame it will
	 * manage events for.
	 * @param frame the JFrame this listener will listen to
	 */
	public WindowInput(JFrame frame)
	{
		frame.addWindowListener(this);
	}
	
	@Override
	public void clear()
	{
		// Not used
	}
	
	@Override
	public void poll()
	{
		// Not used
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		// Indicate to the current game state that it needs to quit
		GameState.indicateQuit();
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}
	
	@Override
	public void windowClosed(WindowEvent e)
	{
		System.exit(0);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		focusLost();
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		focusLost();
	}
	
	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}
	
	/** Should be called when the frame has lost focus. */
	private void focusLost()
	{
		// Must be done because window focus loss will lose key release events
		InputManager.clearAllInputs();
	}
}
