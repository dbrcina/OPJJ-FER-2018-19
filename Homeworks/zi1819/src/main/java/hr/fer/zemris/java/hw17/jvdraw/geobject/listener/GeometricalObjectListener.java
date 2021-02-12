package hr.fer.zemris.java.hw17.jvdraw.geobject.listener;

import hr.fer.zemris.java.hw17.jvdraw.geobject.GeometricalObject;

/**
 * An implementation of <i>Observer</i> from <i>Observer Design Pattern</i>
 * which listens on {@link GeometricalObject} type objects.
 * 
 * @author dbrcina
 *
 */
public interface GeometricalObjectListener {

	/**
	 * Notifies listener about change of object.
	 * 
	 * @param o geometrical object.
	 */
	void geometricalObjectChanged(GeometricalObject o);
}
