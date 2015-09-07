package main.gfx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;
import javax.swing.*;

/** Represents one layer in a layered pane. */
public class Layer extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	/** Width of the layer. */
	private int width;
	/** Height of the layer. */
	private int height;
	/** The buffered image for this surface. */
	private BufferedImage buffImg;
	/** The buffered image's surface for this layer. */
	private Graphics2D buffSurf;
	
	/** The normal constructor for a Layer.
	 * @param width of the layer
	 * @param height of the layer
	 */
	public Layer(int newWidth, int newHeight)
	{
		width = newWidth;
		height = newHeight;
		// Setup the buffer for this layer
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		buffSurf = buffImg.createGraphics();
	}
	
	/** Gets the buffered surface of this frame to draw on.
	 * @return (Graphics2D) The buffered drawing surface
	 */
	public Graphics2D getDrawingSurface()
	{
		return buffSurf;
	}
	
	public synchronized void paintComponent(Graphics g)
	{
//		Graphics2D g2 = (Graphics2D)g;
//		g2.drawImage(buffImg, 0, 0, Gfx.getRenderAreaWidth(), Gfx.getRenderAreaHeight(), null);
	}
	
	
	/** Temporary test implementation of layer updating.
	 * Delete this once true <tt>JLayeredPane</tt> layering is completed.
	 * @param g2 the graphics context for the primary frame
	 */
	@Deprecated
	public synchronized void flip(Graphics2D g2)
	{
		g2.drawImage(
				buffImg,
				0,
				0,
				Gfx.getRenderAreaWidth(),
				Gfx.getRenderAreaHeight(),
				null
				);
	}
	
	/** TODO: figure out how to clear a transparent surface. */
	public void clear()
	{
		
	}
}
