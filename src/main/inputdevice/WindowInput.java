package main.inputdevice;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

import main.gamestate.GameState;
import main.gfx.Gfx;

/** Event listener for window-related events. */
public class WindowInput extends InputDevice implements WindowListener, ComponentListener
{
	/** The frame this window is listening to. */
	private JFrame thisFrame;
	/** If the window is currently visible. */
	private boolean visible = true;
	
	/** Constructor which takes a reference to the frame it will
	 * manage events for.
	 */
	public WindowInput(JFrame frame)
	{
		thisFrame = frame;
		frame.addWindowListener(this);
		frame.addComponentListener(this);
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
	
	/** Indicates when this window is not minimized, etc. */
	public boolean isVisible()
	{
		return visible;
	}
	
	/** Sets the variable that indicates if this window is visible. */
	private void setVisible(boolean visibility)
	{
		visible = visibility;
	}
	
	/* WindowListener Events */
	@Override
	public void windowClosing(WindowEvent e)
	{
		// Indicate to the current game state that it needs to quit
		GameState.indicateQuit();
		setVisible(false);
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
		setVisible(false);
	}
	
	@Override
	public void windowDeiconified(WindowEvent e)
	{
		setVisible(true);
	}
	
	/* ComponentListener Events */
	@Override
	public void componentHidden(ComponentEvent e)
	{
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		Gfx.frameResized(thisFrame);
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
	}
	
	/** Should be called when the frame has lost focus. */
	private void focusLost()
	{
		// Must be done because window focus loss will lose key release events
		InputManager.clearAllInputs();
		GameState.indicateSuspend();
	}
}
