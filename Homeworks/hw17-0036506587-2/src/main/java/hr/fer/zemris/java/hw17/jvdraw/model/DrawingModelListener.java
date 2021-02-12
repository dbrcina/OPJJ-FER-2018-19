package hr.fer.zemris.java.hw17.jvdraw.model;

/**
 * An implementation of <i>Observer</i> from <i>Observer pattern design</i>.<br>
 * It listens on {@link DrawingModel} objects.
 * 
 * @author dbrcina
 *
 */
public interface DrawingModelListener {

	/**
	 * Notification method about adding objects to model.
	 * 
	 * @param source model.
	 * @param index0 index0.
	 * @param index1 index1.
	 */
	void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Notification method about removing objects from model.
	 * 
	 * @param source model.
	 * @param index0 index0.
	 * @param index1 index1.
	 */
	void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Notification method about changes of objects from model.
	 * 
	 * @param source model.
	 * @param index0 index0.
	 * @param index1 index1.
	 */
	void objectsChanged(DrawingModel source, int index0, int index1);
}
