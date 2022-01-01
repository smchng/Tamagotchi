package interactions;

import java.awt.Dimension;
import java.awt.Graphics2D;


/*
 *This interface helps with the layering of images 
 */
public interface Decorator {
	public void draw(Graphics2D g2, Dimension wnd);
}
