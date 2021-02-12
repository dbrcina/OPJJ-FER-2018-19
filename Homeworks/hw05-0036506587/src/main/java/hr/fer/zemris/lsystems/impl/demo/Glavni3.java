package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Application used to test .txt examples which you can find in examples folder.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class Glavni3 {

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments through command line.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}

}
