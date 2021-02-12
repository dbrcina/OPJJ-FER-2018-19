package hr.fer.zemris.java.hw07.observer2;

/**
 * Program that demonstrates <i>Observer pattern</i>.
 * 
 * @author dbrcina
 *
 */
public class ObserverExample {

	/**
	 * Main entry of this program.
	 * 
	 * @param args argument given through command line.
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new SquareValue());

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
