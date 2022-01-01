package interactions;

import java.awt.Dimension;

import graphics.Background;
import graphics.Icons;
import graphics.Pet;
import graphics.Poo;
/*
 * This class creates the objects to reduce instantiating in panel
 */

public class ObjectsConcreteFactory extends ObjectsFactory{

	@Override
	public Objects createObject(String str, Dimension wnd) {
		Objects obj = null;
		if (str=="icons") obj = new Icons();
		else if (str=="pet") obj = new Pet(wnd);
		else if (str=="bg") obj = new Background();
		else if (str=="poo") obj = new Poo();
		return obj;
	}

}
