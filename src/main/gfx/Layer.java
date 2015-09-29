package main.gfx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	/** The ratio CurrentWidth/DefaultWidth for the width of this layer. */
	private double scaleFactor;
	
	/** The normal constructor for a Layer.
	 * @param width of the layer
	 * @param height of the layer
	 */
	public Layer(int newWidth, int newHeight)
	{
		adjustSize(new Dimension(newWidth, newHeight));
	}
	
	private void createNewBuffer()
	{
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		buffSurf = buffImg.createGraphics();
	}
	
	/** Gets the buffered surface of this frame to draw on.
	 * @return (Graphics2D) The buffered drawing surface
	 */
	public synchronized Graphics2D getDrawingSurface()
	{
		return buffSurf;
	}
	
	/** Gets the width of this layer. */
	public int getLayerWidth()
	{
		return width;
	}
	
	/** Gets the height of this layer. */
	public int getLayerHeight()
	{
		return height;
	}
	
	/** Gets the scale factor for this layer. */
	public double getScaleFactor()
	{
		return scaleFactor;
	}
	
	@Override
	public synchronized void paintComponent(Graphics g)
	{
	}
	
	/** Draws this layer to the specified surface.
	 * @param g2 the graphics context to draw to
	 */
	public synchronized void flip(Graphics2D g2)
	{
		g2.drawImage(
				buffImg,
				0,
				0,
				getLayerWidth(),
				getLayerHeight(),
				null
				);
		if (main.Game.debugEnabled())
		{
			Color oC = g2.getColor();
			g2.setColor(Color.cyan);
			g2.drawRect(0, 0, getLayerWidth()-1, getLayerHeight()-1);
			g2.setColor(oC);
		}
	}
	
	/** Clears the buffer of this layer. */
	public synchronized void clear()
	{
		// Get the old composite to reset to
		Composite oldComp = buffSurf.getComposite();
		// Change the composite
		buffSurf.setComposite(AlphaComposite.Clear);
		// Clear the surface
		buffSurf.fillRect(0, 0, Gfx.getFrameWidth(), Gfx.getFrameHeight());
		// Set the composite back to the original
		buffSurf.setComposite(oldComp);
	}
	
	/** Adjusts the dimensions of this layer. */
	public synchronized void adjustSize(Dimension newDims)
	{
		width = newDims.width;
		height = newDims.height;
		this.setSize(newDims);
		createNewBuffer();
		updateScaleFactor();
	}
	
	private void updateScaleFactor()
	{
		scaleFactor = (double)getWidth()/(double)Gfx.DEFAULT_WINDOW_DIM;
	}
}
