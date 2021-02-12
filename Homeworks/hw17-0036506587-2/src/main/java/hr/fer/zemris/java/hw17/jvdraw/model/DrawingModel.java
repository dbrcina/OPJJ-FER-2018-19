package hr.fer.zemris.java.hw17.jvdraw.model;

import hr.fer.zemris.java.hw17.jvdraw.geobject.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geobject.listener.GeometricalObjectListener;

/**
 * Main model of <b>Paint</b> program.
 * 
 * <p>
 * It contains collection of {@link GeometricalObject} that are being drawn onto
 * canvas and all appropriate listeners and method.
 * </p>
 * 
 * @author HP
 *
 */
public interface DrawingModel extends GeometricalObjectListener {

	/**
	 * Retrieve number of objects stored in <b>this</b> model.
	 * 
	 * @return number of objects.
	 */
	int getSize();

	/**
	 * Retrieve object from position <i>index</i>.
	 * 
	 * @param index index
	 * @return object.
	 */
	GeometricalObject getObject(int index);

	/**
	 * Add <i>object</i> to <b>this</b> model.
	 * 
	 * @param object object.
	 */
	void add(GeometricalObject object);

	/**
	 * Removes <i>object</i> from <b>this</b> model.
	 * 
	 * @param object object.
	 */
	void remove(GeometricalObject object);

	/**
	 * Changes order of <i>object</i> by <i>offset</i> in <b>this</b> model.
	 * 
	 * @param object object.
	 * @param offset offset.
	 */
	void changeOrder(GeometricalObject object, int offset);

	/**
	 * Retrieves index of <i>object</i> from <b>this</b> model;
	 * 
	 * @param object object.
	 * @return index of object or -1 if it doesn't exist.
	 */
	int indexOf(GeometricalObject object);

	/**
	 * Clears <b>this</b> model.
	 */
	void clear();

	/**
	 * Refreshes modified flag.
	 */
	void clearModifiedFlag();

	/**
	 * Checks whether model was modified.
	 * 
	 * @return <code>true</code> if it was, otherwise <code>false</code>.
	 */
	boolean isModified();

	/**
	 * Adds listener <i>l</i> to list of listeners.
	 * 
	 * @param l listener.
	 */
	void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes listener <code>false</code>from list of listeners.
	 * 
	 * @param l listener.
	 */
	void removeDrawingModelListener(DrawingModelListener l);

}
