package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements functional interface {@link Command}. It provides user
 * to change direction of turtle by rotating it by specified angle.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class RotateCommand implements Command {

	/**
	 * Angle in radians by which turtle needs to be rotated.
	 */
	private double angle;

	/**
	 * Constructor that takes angle as an argument.
	 * 
	 * @param angle the input for {@link #angle}.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setDirection(currentState.getDirection().rotated(angle));

	}

}
