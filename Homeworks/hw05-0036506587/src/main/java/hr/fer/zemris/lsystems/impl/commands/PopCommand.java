package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements functional interface {@link Command} and represents pop
 * command which removes current {@link TurtleState} state from stack modeled by
 * {@link ObjectStack}.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class PopCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
