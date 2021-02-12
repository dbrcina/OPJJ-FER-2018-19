package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that implements functional interface {@link Command} and represents
 * push command which puts current {@link TurtleState} state on stack modeled by
 * {@link ObjectStack}.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());

	}

}
