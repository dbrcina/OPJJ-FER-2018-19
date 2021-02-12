package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.impl.TurtleState;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class that implements functional interface {@link Command}. This command
 * changes turtle's current color as determined by users input.
 * 
 * @author dbrcina
 * @version 1.0
 * @see TurtleState#setColor(Color)
 *
 */
public class ColorCommand implements Command {

	/**
	 * Turtle's new color.
	 */
	private Color color;

	/**
	 * Constructor that takes color as an argument.
	 * 
	 * @param color the input argument for {@link #color}.
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
