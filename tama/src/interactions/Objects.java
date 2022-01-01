package interactions;

import java.awt.Dimension;
import java.awt.Graphics2D;


/*The interface that requires the implemented classes to define
 * 
 */
public interface Objects {
	public void draw(Graphics2D g2, Dimension wnd, int gameState);
	public void actionText(Graphics2D g, Dimension wnd, int gameState);
}
