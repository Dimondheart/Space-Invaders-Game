package main;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/** Handles the playing of all sounds and managing sound resources.
 * <br>Is threaded.
 */
public class SoundSystem implements Runnable
{
	/** Sound system's thread. */
	private Thread thread;
	/** Sound system's cycle handler. */
	private ThreadClock clock;
	/** List of playing or queued sound effects. */
	private LinkedList<Sound> sfx;
	/** Queue of BGM songs to play, will currently ignore all but the last
	 * sound.
	 */
	private LinkedList<Sound> bgm;
	
	protected enum PlayState
	{
		NOT_STARTED,
		PLAYING,
		DONE,
		PENDING_DELETION
	}
	
	public SoundSystem()
	{
		System.out.println("Setting Up Sound System...");
		thread = new Thread(this);
		clock = new ThreadClock(ThreadClock.LOW_PRIORITY);
		sfx = new LinkedList<Sound>();
	}
	
	/** Start the sound system. */
	public void start()
	{
		System.out.println("Starting Sound System...");
		thread.start();
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			// Handle sound effects, lock access while processing
			synchronized (this)
			{
				for (int i = 0; i < sfx.size(); ++i)
				{
					// Get the sound file
					Sound sound = sfx.get(i);
					// Handle the sound state
					switch (sound.getPlayState())
					{
						case NOT_STARTED:
							sound.play();
							break;

						case PENDING_DELETION:
							break;

						case PLAYING:
							break;

						// Done playing, release resources used
						case DONE:
							// Fall through

						default:
							sound.cleanup();
					}
				}
				// TODO: figure out how to use LinkedList.removeIf() or LL.removeAll()
				while (true)
				{
					// If list of sounds is empty
					if (sfx.size() == 0)
					{
						break;
					}
					// Try to delete the first sound
					else if (sfx.get(0).getPlayState() == PlayState.PENDING_DELETION)
					{
						sfx.remove(0);
						continue;
					}
					// Try to delete the second sound
					else if (sfx.size() > 0 && sfx.get(1).getPlayState() == PlayState.PENDING_DELETION)
					{
						sfx.remove(1);
						continue;
					}
					else
					{
						break;
					}
				}
			}
			// TODO: Handle BGM
			clock.nextTick();
		}
	}
	
	/** Play the specified sound file.
	 * @param name the name of the sound file (in ~/res/sfx) with the
	 * extension.
	 */
	public static synchronized void playSound(String name)
	{
		// TODO: Open the file and create a Sound with that file
		// TODO: Add the sound to sfx
	}
	
	/** Changes to the specified background music.
	 * @param name the name of the BGM file (in ~/res/bgm) with the extension.
	 */
	public static synchronized void changeBGM(String name)
	{
		// TODO: Open the file and create a ??? object with that file
		// TODO: Add the sound to bgm list
	}
	
	/** Represents a single play request of a sound.
	 * Takes a string specifying the relative path to the sound file.
	 */
	private class Sound
	{
		/** Audio data stream for this sound. */
		private AudioInputStream audioStream;
		private Clip clip;
		private PlayState playState;
		
		/** Constructor, takes a string for the path to the file to play. */
		public Sound(File file)
		{
			// Set state to not started yet
			playState = PlayState.NOT_STARTED;
			// Setup playback clip
			try
			{
				clip = AudioSystem.getClip();
			}
			catch (LineUnavailableException e)
			{
				System.out.println("ALL SOUND LINES FILLED OR UNAVAILABLE");
				return;
			}
		}
		
		public synchronized PlayState getPlayState()
		{
			return playState;
		}
		
		/** Starts playback of this sound. */
		public synchronized void play()
		{
			// TODO: Implement sound playing
			// Dummy transition/stopping
			if (playState == PlayState.PLAYING)
			{
				playState = PlayState.DONE;
			}
			else
			{
				playState = PlayState.PLAYING;
			}
		}
		
		/** Releases or cleans up any resources used by this sound. */
		public synchronized void cleanup()
		{
			// Close the audio input stream
			try
			{
				audioStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			// Close the clip
			clip.close();
			// Indicate this sound can be deleted safely
			playState = PlayState.PENDING_DELETION;
		}
	}
}
