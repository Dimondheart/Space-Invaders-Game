package main;

/** Manages timing for the thread of the object that instantiates it. */
public class ThreadClock
{
	// Thread priorities, affects time per cycle
	public static final int LOW_PRIORITY = 10;
	public static final int MEDIUM_PRIORITY = 25;
	public static final int HIGH_PRIORITY = 50;
	
	// Calculated based on the priority setting
	private long maxCPS;
	private long maxMSPerCycle;
	
	// Actual average CPS
	// TODO: Add something to calculate this
	public double cps;

	// Times recorded for cycle speed control
	private long start;
	private long stop;
	private long tickDuration;

	/** Default constructor, default priority is MEDIUM_PRIORITY. */
	public ThreadClock()
	{
		// Set priority to the default value
		setPriority(MEDIUM_PRIORITY);
		// Start the current tick
		startTick();
	}
	
	/** Constructor, takes a specified priority level. */
	public ThreadClock(int priority)
	{
		setPriority(priority);
		startTick();
	}
	
	/** Changes the priority of this thread. */
	public void setPriority(int priority)
	{
		maxCPS = (long)priority;
		maxMSPerCycle = 1000 / maxCPS;
	}
	
	/** Handles moving to the next tick.
	 * This includes pausing the thread if the cycle took less than the desired
	 * amount of time to run.
	 */
	public void nextTick()
	{
		// Stop time (not literally, just get the current system time...)
		stop = System.currentTimeMillis();
		// Calculate tick duration
		tickDuration = stop - start;
		// Processing finished before max cycle time, pause the thread
		if (tickDuration < maxMSPerCycle)
		{
			/* Pause the thread until the desired cycle time
			 * Varies slightly based on the system timer's resolution
			 */
			pauseThread(maxMSPerCycle - tickDuration);
		}
		// Start time for the next tick
		startTick();
	}

	/** Sets the start time for the next tick. */
	private void startTick()
	{
		// Cycle start time
		start = System.currentTimeMillis();
	}

	/** Puts the thread to sleep for the specified number of milliseconds. */
	private void pauseThread(long millisec)
	{
		try
		{
			Thread.sleep(millisec);
		}
		catch (InterruptedException e)
		{
		}
	}
}
