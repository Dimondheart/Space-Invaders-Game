package main.inputdevice;

/** Manages all of the input devices. */
public class InputManager
{
	public static KeyboardInput keyboard;
	public static MouseInput mouse;
	public static WindowInput window;
	
	/** Basic constructor. */
	public InputManager()
	{
		keyboard = new KeyboardInput(main.gfx.Gfx.getPrimaryFrame());
		mouse = new MouseInput(main.gfx.Gfx.getPrimaryFrame());
		window = new WindowInput(main.gfx.Gfx.getPrimaryFrame());
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
