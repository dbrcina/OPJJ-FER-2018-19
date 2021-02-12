package hr.fer.zemris.java.hw17.jvdraw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.hw17.jvdraw.geobject.GeometricalObject;

/**
 * An implementation of {@link DrawingModelListener} which is represented
 * through {@link AbstractListModel}.
 * 
 * @author dbrcina
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Drawing model.
	 */
	private DrawingModel model;
	/**
	 * List of listeners.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();

	/**
	 * Constructor used for initialization.
	 * 
	 * @param model model.
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		notifyListeners(l -> l.intervalAdded(event));
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		notifyListeners(l -> l.intervalRemoved(event));
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		notifyListeners(l -> l.contentsChanged(event));
	}

	/**
	 * Notifies listeners about <i>action</i>.
	 * 
	 * @param action action.
	 */
	private void notifyListeners(Consumer<ListDataListener> action) {
		listeners.forEach(l -> action.accept(l));
	}
}
