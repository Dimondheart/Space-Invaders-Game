package main.inputdevice;

import main.gfx.Gfx;

/** Manages all of the input devices. */
public class InputManager
{
	private static KeyboardInput keyboard;
	private static MouseInput mouse;
	private static WindowInput window;
	
	/** Static initialization. */
	static
	{
		keyboard = new KeyboardInput(Gfx.getPrimaryFrame());
		mouse = new MouseInput(Gfx.getPrimaryFrame());
		window = new WindowInput(Gfx.getPrimaryFrame());
	}
	
	/** Gets the current keyboard.
	 * @return (KeyboardInput) the current keyboard
	 */
	public static KeyboardInput getKeyboard()
	{
		return keyboard;
	}
	
	/** Gets the current mouse.
	 * @return (MouseInput) the current mouse
	 */
	public static MouseInput getMouse()
	{
		return mouse;
	}
	
	/** Gets the primary window. */
	public static WindowInput getPrimaryWindow()
	{
		return window;
	}
	
	/** Clears the stored data of all input devices. */
	public static void clearAllInputs()
	{
		keyboard.clear();
		mouse.clear();
		window.clear();
	}
	
	/** Updates the stored data used by input devices. */
	public static void updateAllInputs()
	{
		keyboard.poll();
		mouse.poll();
		window.poll();
	}
}
