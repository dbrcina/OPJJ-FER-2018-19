package hr.fer.zemris.java.console.messages;

/**
 * This class provides some messages that are sent to the user while working in
 * a <i>shell-like</i> console.
 * 
 * @author dbrcina
 *
 */
public class ConsoleMessage {

	/**
	 * Welcome message.
	 */
	public static final String WELCOME_MSG = "Simulacija \"google\" tražilice.";
	/**
	 * Input command message.
	 */
	public static final String INPUT_CMD_MSG = "Unesi naredbu(exit za kraj) > ";
	/**
	 * Invalid command message.
	 */
	public static final String INVALID_CMD_MSG = "Nepoznata naredba.\n";
	/**
	 * Exit message.
	 */
	public static final String END_MSG = "Kraj.";
	/**
	 * This message is sent when arguments for <b><i>exit</i></b> command are
	 * invalid.
	 */
	public static final String EXIT_INV_ARG = "Naredba \"exit\" ne očekuje nikakve argumente.\n";
	/**
	 * No results message.
	 */
	public static final String NO_RESULTS = "Nema rezultata.\n";
	/**
	 * This message is sent when arguments for <b><i>results</i></b> command are
	 * invalid.
	 */
	public static final String RESULTS_INV_ARG = "Naredba \"results\" ne očekuje nikakve argumente.\n";
	/**
	 * This message is sent when arguments for <b><i>type</i></b> command are
	 * invalid.
	 */
	public static final String TYPE_INV_ARG = "Naredba \"type\" očekuje jedan argument, "
			+ "redni broj rezultata dobivenih uspješnom \"query\" naredbom.\n";
	/**
	 * This message is sent when <b><i>type</i></b> command fails.
	 */
	public static final String TYPE_ERROR = "Nemoguće je izvršiti naredbu \"type\" na nepostojećim rezultatima.\n"
			+ "Moguće pogreške: naredba je pozvana neovisno o \"query\" naredbi "
			+ "ili \"query\" naredba nije vratila nikakve rezultate.\n";
	/**
	 * This message is sent when <b><i>type</i></b> command recieves invalid
	 * position.
	 */
	public static final String TYPE_INDEX_ERROR = "Zadan je pogrešan index rezultata.\n";
	/**
	 * This message is sent when arguments for <b><i>query</i></b> command are
	 * invalid.
	 */
	public static final String QUERY_INV_ARG = "Naredba \"query\" očekuje proizvoljne riječi kao argumente.\n";
}
