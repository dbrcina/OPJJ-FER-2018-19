package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * This class implements interface {@link LSystemBuilder}. It provides an
 * implementation which creates a new object that is created by method
 * {@link #build()}. That object, which type is {@link LSystemBuilder}, is shown
 * on a window through interface {@link LSystemViewer} by method
 * {@link LSystemViewer#showLSystem(LSystem)}.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * It represents turtle's unit displacement. Initially, value is
	 * <code>0.1</code>.
	 */
	private double unitLength = 0.1;

	/**
	 * It represents a scaler that is used when turtle's effective length needs to
	 * be controled so that fractal's dimensions would stay constant. Initially,
	 * value is <code>1</code>.
	 */
	private double unitLengthDegreeScaler = 1;

	/**
	 * Start position in coordinate system. Initially, position is set to the bottom
	 * left corner of a window.
	 */
	private Vector2D origin = new Vector2D(0, 0);

	/**
	 * Angle to positive x-axis. Initially, turtle is directed to the right.
	 */
	private double angle = 0;

	/**
	 * Input string.
	 */
	private String axiom = "";

	/**
	 * Dictionary-like collections that register all productions by right symbol.
	 */
	private Dictionary<Character, String> registeredProductions;

	/**
	 * Dictionary-like collection that register all commands by right symbol.
	 */
	private Dictionary<Character, Command> registeredCommands;

	/**
	 * Default constructor.
	 */
	public LSystemBuilderImpl() {
		registeredProductions = new Dictionary<>();
		registeredCommands = new Dictionary<>();
	}

	/**
	 * Setter for {@link #unitLength}.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Setter for {@link #origin}.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Setter for {@link #angle}.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Setter for {@link #axiom}.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Setter for {@link #unitLengthDegreeScaler}.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	/**
	 * Registers <code>production</code> by <code>symbol</code>.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		registeredProductions.put(symbol, production);
		return this;
	}

	/**
	 * Registers <code>action</code> by <code>symbol</code>.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		// split by empty spaces
		String[] parts = action.toLowerCase().split("\\s+");

		// switch case for commands
		switch (parts[0]) {

		// case for draw command
		case "draw":
			double stepToDraw = parseNumber(parts[0], parts[1]);
			registeredCommands.put(symbol, new DrawCommand(stepToDraw));
			break;

		// case for skip command
		case "skip":
			double stepToSkip = parseNumber(parts[0], parts[1]);
			registeredCommands.put(symbol, new SkipCommand(stepToSkip));
			break;

		// case for scale command
		case "scale":
			double scale = parseNumber(parts[0], parts[1]);
			registeredCommands.put(symbol, new ScaleCommand(scale));
			break;

		// case for rotate command
		case "rotate":
			// convert from radians to degrees
			double angle = parseNumber(parts[0], parts[1]) * Math.PI / 180;
			registeredCommands.put(symbol, new RotateCommand(angle));
			break;

		// case for push command
		case "push":
			registeredCommands.put(symbol, new PushCommand());
			break;

		// case for pop command
		case "pop":
			registeredCommands.put(symbol, new PopCommand());
			break;

		// case for color command
		case "color":
			registeredCommands.put(symbol, new ColorCommand(Color.decode("0x" + parts[1])));
			break;

		// this probably wont happen, but lets keep it safe.
		default:
			generateError("Command je nepoznat!");
		}

		return this;
	}

	/**
	 * Method that from input text <code>lines</code> generates right configuration.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		// iterate through input text
		for (String line : lines) {

			// if current line is empty, skip it
			if (line.isEmpty()) {
				continue;
			}

			// split line by empty spaces
			String[] parts = line.split("\\s+");

			// switch case for properties
			switch (parts[0]) {

			// case for origin
			case "origin":
				configureOrigin(parts);
				break;

			// case for angle
			case "angle":
				configureAngle(parts);
				break;

			// case for unit length
			case "unitLength":
				configureUnitLength(parts);
				break;

			// case for unit length degree scaler
			case "unitLengthDegreeScaler":
				configureUnitLengthDegreeScaler(parts);
				break;

			// case for command
			case "command":
				configureCommand(parts);
				break;

			// case for axiom
			case "axiom":
				configureAxion(parts);
				break;

			// case for production
			case "production":
				configureProduction(parts);
				break;

			// this probably wont happen, but lets keep it safe.
			default:
				generateError("Nepoznat unos u konfiguraciju!");
			}
		}
		return this;
	}

	/**
	 * Method used for building a picture with all specified properties. It returns
	 * new instance of {@link LSystem} which is interface, so methods
	 * {@link LSystem#draw(int, Painter)} and {@link LSystem#generate(int)} must be
	 * implemented.
	 * 
	 * @return new instance of {@link LSystem}.
	 */
	@Override
	public LSystem build() {
		return new LSystem() {

			@Override
			public String generate(int level) {
				if (level == 0) {
					return axiom;
				}

				String currentAxiom = axiom;
				StringBuilder newAxiom = new StringBuilder();

				while (level > 0) {
					for (int i = 0; i < currentAxiom.length(); i++) {
						String production = registeredProductions.get(currentAxiom.charAt(i));
						if (production != null) {
							newAxiom.append(production);
						} else {
							// if production does not exist..mostly productions will be stored by only one
							// char F
							newAxiom.append(currentAxiom.charAt(i));
						}
					}

					currentAxiom = newAxiom.toString();
					newAxiom.setLength(0);
					level--;
				}

				return currentAxiom;
			}

			@Override
			public void draw(int level, Painter painter) {
				Context ctx = new Context();

				// new turtle's state..initial color is black, direction is determined by angle
				// and effective length is calculated like unitLenght *
				// unitLengthDegreeScaler^level.
				TurtleState state = new TurtleState(origin, new Vector2D(1, 0).rotated(angle), Color.BLACK,
						unitLength * Math.pow(unitLengthDegreeScaler, level));
				ctx.pushState(state);

				char[] symbols = generate(level).toCharArray();
				for (char symbol : symbols) {
					Command command = registeredCommands.get(symbol);
					if (command != null) {
						command.execute(ctx, painter);
					}

				}
			}
		};
	}

	/**
	 * Helper method for origin configuration.
	 * 
	 * @param parts parts.
	 */
	private void configureOrigin(String[] parts) {
		if (parts.length != 3) {
			generateError("Broj argumenata za origin nije dobro zadan!");
		}
		double x = parseNumber(parts[0], parts[1]);
		double y = parseNumber(parts[0], parts[2]);
		setOrigin(x, y);
	}

	/**
	 * Helper method for angle configuration.
	 * 
	 * @param parts parts.
	 */
	private void configureAngle(String[] parts) {
		if (parts.length != 2) {
			generateError("Broj argumenata za " + parts[0] + " nije dobro zadan!");
		}
		// from radians to degrees
		double angle = parseNumber(parts[0], parts[1]) * Math.PI / 180;
		setAngle(angle);
	}

	/**
	 * Helper method for unit length configuration.
	 * 
	 * @param parts parts.
	 */
	private void configureUnitLength(String[] parts) {
		if (parts.length != 2) {
			generateError("Broj argumenata za " + parts[0] + " nije dobro zadan!");
		}
		double unitLength = parseNumber(parts[0], parts[1]);
		setUnitLength(unitLength);
	}

	/**
	 * Helper method for unit length degree scaler configuration.
	 * 
	 * @param parts parts.
	 */
	private void configureUnitLengthDegreeScaler(String[] parts) {
		if (parts.length < 2 || parts.length > 4) {
			generateError("Broj argumenata za " + parts[0] + " nije dobro zadan!");
		}

		// copy remain parts into another array
		String[] scalerParts = Arrays.copyOfRange(parts, 1, parts.length);

		double scaler;

		// if scale value is only one number and not an expression
		if (scalerParts.length == 1) {
			scaler = parseNumber(parts[0], scalerParts[0]);
		}

		// otherwise, fetch right numbers and divide them
		else {
			// create String from remains by using Stream API
			String scalerPart = Arrays.stream(scalerParts).collect(Collectors.joining(""));
			// split the numbers by division sign /
			String[] numbers = scalerPart.split("/");
			scaler = parseNumber(parts[0], numbers[0]) / parseNumber(parts[0], numbers[1]);
		}

		setUnitLengthDegreeScaler(scaler);
	}

	/**
	 * Helper method for command configuration.
	 * 
	 * @param parts parts.
	 */
	private void configureCommand(String[] parts) {
		if (parts.length != 3 && parts.length != 4) {
			generateError("Broj argumenata za " + parts[0] + " nije dobro zadan!");
		}

		char symbol = parts[1].charAt(0);
		String command = null;

		// if command is pop or push
		if (parts.length == 3) {
			command = parts[2];
		}

		// any other ones
		else {
			command = parts[2] + " " + parts[3];
		}

		registerCommand(symbol, command);
	}

	/**
	 * Helper method for axiom configuration
	 * 
	 * @param parts parts.
	 */
	private void configureAxion(String[] parts) {
		if (parts.length != 2) {
			generateError("Broj argumenata za " + parts[0] + " nije dobro zadan!");
		}

		setAxiom(parts[1]);
	}

	/**
	 * Helper method for production configuration.
	 * 
	 * @param parts parts.
	 */
	private void configureProduction(String[] parts) {
		if (parts.length != 3) {
			generateError("Broj argumenata za " + parts[0] + " nije dobro zadan!");
		}

		char axiom = parts[1].charAt(0);
		registerProduction(axiom, parts[2]);
	}

	/**
	 * Helper method that parses given <code>number</code> as determined by
	 * <code>cause</code> into double number if it is possible, otherwise,
	 * {@link NumberFormatException} is thrown and handled by
	 * {@link #generateError(String)} method.
	 * 
	 * @param command command
	 * @param number  number that needs to be parsed.
	 * @return parsed number.
	 */
	private double parseNumber(String cause, String number) {
		double d = 0;
		try {
			d = Double.parseDouble(number);
		} catch (NumberFormatException e) {
			generateError("Došlo je do greške kod parsiranja broja za  " + cause + "!");
		}
		return d;
	}

	/**
	 * Helper method that throws new {@link IllegalArgumentException} for the given
	 * <code>cause</code>.
	 * 
	 * @param cause cause.
	 */
	private void generateError(String msg) {
		throw new IllegalArgumentException(msg);
	}
}
