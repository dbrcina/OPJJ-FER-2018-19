package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements functional interface {@link Command}. This command
 * scales turtle's effective length by given factor and in the end sets it by
 * using method {@link TurtleState#setEffectiveLength(double)}.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ScaleCommand implements Command {

	/**
	 * Scaler for turtle's effective length.
	 */
	private double factor;

	/**
	 * Constructor that takes factor as an argument.
	 * 
	 * @param factor the input argument for {@link #factor}.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setEffectiveLength(factor * currentState.getEffectiveLength());

	}

}
