package interactions;

import java.awt.Dimension;

/*
 * This class makes the implemented classes add the method
 */

public abstract class ObjectsFactory {
	public abstract Objects createObject(String str, Dimension wnd);
}
