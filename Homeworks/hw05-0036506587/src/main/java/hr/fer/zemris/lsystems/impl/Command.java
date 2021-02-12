package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Functional interface which represents a contract between every class that
 * implements this interface that needs to fulfilled. In other words, every
 * class needs to provide an implementation of
 * {@link #execute(Context, Painter)} method.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
@FunctionalInterface
public interface Command {

	/**
	 * Method that for given {@link Context} <code>ctx</code> and {@link Painter}
	 * <code>painter</code> does what user wants to.
	 * 
	 * @param ctx     the input argument for context.
	 * @param painter the input argument for painter.
	 */
	void execute(Context ctx, Painter painter);
}
