package main.gfx;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

/** Manages the loading, storing, scaling, and other operations related to
 * graphics files.
 */
public class GraphicsManager
{
	private static final String GRAPHICS_DIRECTORY = "main/resources/graphics/";
	private ConcurrentHashMap<String, BufferedImage> graphics;
	private ConcurrentHashMap<String, Image> scaledGfx;
	
	public GraphicsManager()
	{
		graphics = new ConcurrentHashMap<String, BufferedImage>();
		scaledGfx = new ConcurrentHashMap<String, Image>();
	}
	
	/** Gets a graphic that does not need to be scaled. */
	public synchronized BufferedImage getGraphic(String relPath)
	{
		// Return the graphic if already loaded
		if (graphics.containsKey(relPath))
		{
			return graphics.get(relPath);
		}
		// Graphic not already loaded, load it
		else
		{
			System.out.println(
					"WARNING: Loading graphic file JIT. File: " + relPath
					);
			loadGraphic(relPath);
			return graphics.get(relPath);
		}
	}
	
	/** Gets a scaled version of the specified graphic with the specified
	 * scaling factors
	 * <br>NOTE: This does not yet scale the image, it only returns the
	 * original.
	 * @param relPath same as to call getGraphic(relPath)
	 * @param hScale
	 * @param vScale
	 * @return (Image) the scaled image
	 */
	public synchronized Image getScaledGraphic(String relPath, double hScale, double vScale)
	{
		// Determine the scaled dimensions
		int newW = (int)((double)getGraphic(relPath).getWidth(null) * hScale);
		int newH = (int)((double)getGraphic(relPath).getHeight(null) * vScale);
		int prevW = -10;
		int prevH = -10;
		// Update prev scaled image sizes if the image exists
		if (scaledGfx.containsKey(relPath))
		{
			prevW = scaledGfx.get(relPath).getWidth(null);
			prevH = scaledGfx.get(relPath).getHeight(null);
		}
		// If the current stored image is not scaled enough
		if (newW != prevW || newH != prevH)
		{
			// Add a new scaled image to the map
			scaledGfx.put(
					relPath,
					getGraphic(relPath).getScaledInstance(
							newW,
							newH,
							Image.SCALE_FAST
							)
					);
		}
		return scaledGfx.get(relPath);
	}
	
	/** Loads the graphic at the specified relative path. */
	public synchronized void loadGraphic(String relPath)
	{
		// Graphic already loaded
		if (graphics.containsKey(relPath))
		{
			return;
		}
		else
		{
			// Get the full path to the file
			File file = new File(
					System.getProperty(
							"user.dir"
							).replace("\\", "/")
					+ "/src/"
					+ GRAPHICS_DIRECTORY
					+ relPath
							);
			BufferedImage bi = null;
			// Load the image
			try
			{
				bi = ImageIO.read(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.exit(0);
			}
			// Add the image to the graphics map
			graphics.put(relPath, bi);
		}
	}
}
