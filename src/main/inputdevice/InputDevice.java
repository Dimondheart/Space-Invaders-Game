package main.inputdevice;

/** The interface for a basic input device, like a mouse or keyboard. */
public interface InputDevice
{
	/** Clears all stored input device data. */
	public void clear();
	/** Process and react to stored input data. */
	public void poll();
}
