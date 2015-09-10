package main.inputdevice;

/** The interface for a basic input device, like a mouse or keyboard. */
public abstract class InputDevice
{
	/** Clears all stored input device data. */
	public abstract void clear();
	/** Process and react to stored input data. */
	public abstract void poll();
}
