package hr.fer.zemris.java.hw17.jvdraw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import hr.fer.zemris.java.hw17.jvdraw.geobject.GeometricalObject;

/**
 * An implementation of {@link DrawingModel} interface.
 * 
 * @author dbrcina
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * List of objects.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * List of listeners.
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	/**
	 * Boolean flag which tells whether this model was modified.
	 */
	private boolean isModified;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(this);
		int index = objects.size();
		notifyListeners(l -> l.objectsAdded(this, index, index));

		isModified = true;
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		objects.remove(object);
		object.removeGeometricalObjectListener(this);
		notifyListeners(l -> l.objectsRemoved(this, index, index));

		isModified = true;
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		if (index + offset > objects.size() - 1 || index + offset < 0) {
			return;
		}
		Collections.swap(objects, index, index + offset);
		notifyListeners(l -> l.objectsChanged(this, index, index));

		isModified = true;
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		objects.clear();
	}

	@Override
	public void clearModifiedFlag() {
		isModified = false;
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o);
		notifyListeners(l -> l.objectsChanged(this, index, index));
	}

	/**
	 * Notifies all listeners about <i>action</i>.
	 * 
	 * @param action action.
	 */
	private void notifyListeners(Consumer<DrawingModelListener> action) {
		listeners.forEach(l -> action.accept(l));
	}
}
