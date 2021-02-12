package hr.fer.zemris.java.hw17.jvdraw.geobject;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.listener.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectVisitor;

/**
 * Abstract definition of a geometrical object. It implements <i>Subject</i>
 * from <i>Observer design pattern</i>.
 * 
 * <p>
 * It provides some basic abstract methods for certain instances of this model
 * like (de)registration of certain listeners on this model, creating editor for
 * depicted instance etc.
 * </p>
 * 
 * @author dbrcina
 *
 */
public abstract class GeometricalObject {

	/**
	 * List of listeners.
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();

	/**
	 * Accepts an action which is provided through visitor <i>v</i>.
	 * 
	 * @param v geometrical object visitor.
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Creates instance of {@link GeometricalObjectEditor} for current geometrical
	 * object.
	 * 
	 * @return new instance of editor.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Registers provided listener <i>l</i> onto this geometrical object.
	 * 
	 * @param l geometrical object listener.
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Dereegisters provided listener <i>l</i> from this geometrical object.
	 * 
	 * @param l geometrical object listener.
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners that listen changes on provided <i>object</i>.
	 * 
	 * @param object geometrical object.
	 */
	protected void notifyChange(GeometricalObject object) {
		object.listeners.forEach(l -> l.geometricalObjectChanged(object));
	}

	/**
	 * Creates file representation of this geometrical object.
	 * 
	 * @return file representation.
	 */
	public abstract String fileRepresentation();

	/**
	 * Copies current object into new instance of {@link GeometricalObject}.
	 * 
	 * @return new instance of {@link GeometricalObject}.
	 */
	public abstract GeometricalObject copy();
}
