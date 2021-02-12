package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that implements functional interface {@link Command}. Implementation is
 * basically the same as for {@link DrawCommand}. Only difference is in that
 * this command does not draws a line but skips it.
 * 
 * @author dbrcina
 * @version 1.0
 * @see {@link DrawCommand} for further documentation.
 *
 */
public class SkipCommand implements Command {

	/**
	 * Value by which turtle needs to move multiplied with turtle's effective
	 * length.
	 */
	private double step;

	/**
	 * Constructor that takes step as an argument
	 * 
	 * @param step the input argument for {@link #step}.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();

		// fetch turtle's current position
		Vector2D position = currentState.getPosition();

		// fetch turtle's current direction
		Vector2D direction = currentState.getDirection();

		// fetch turtle's effective length
		double effectiveLength = currentState.getEffectiveLength();

		// calculate new position for turtle
		Vector2D newPosition = position.translated(direction.scaled(effectiveLength * step));
		currentState.setPosition(newPosition);
	}
}
