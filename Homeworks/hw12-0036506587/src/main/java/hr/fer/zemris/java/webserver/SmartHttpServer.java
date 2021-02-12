package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.webserver.workers.EchoParams;
import hr.fer.zemris.java.webserver.workers.HelloWorker;

/**
 * An implementation of <i>HTTP</i> server.
 * 
 * @author dbrcina
 *
 */
public class SmartHttpServer {

	/**
	 * Session's id length.
	 */
	private static final int SID_LENGTH = 20;
	/**
	 * Server's address.
	 */
	private String address;
	/**
	 * Server's domain name.
	 */
	private String domainName;
	/**
	 * Server's port.
	 */
	private int port;
	/**
	 * Number of working threads.
	 */
	private int workerThreads;
	/**
	 * Session timeout.
	 */
	private int sessionTimeout;
	/**
	 * Map of mime types.
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Thread on which this server is run.
	 */
	private ServerThread serverThread = new ServerThread();
	/**
	 * Thread pool.
	 */
	private ExecutorService threadPool;
	/**
	 * Root path.
	 */
	private Path documentRoot;
	/**
	 * Boolean flag for stoping this server.
	 */
	private volatile boolean stopServer;
	/**
	 * Map of {@link IWebWorker} workers.
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/**
	 * Map of {@link SessionMapEntry} sessions.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	/**
	 * Session random generator.
	 */
	private Random sessionRandom = new Random();
	/**
	 * Thread used to clean up memory.
	 */
	private MemoryCleaner memoryCleaner = new MemoryCleaner();

	/**
	 * Constructor used for initialization.
	 * 
	 * @param configFileName config.
	 */
	public SmartHttpServer(String configFileName) {
		try {
			initProperties(Paths.get(configFileName));
		} catch (IOException e) {
			System.out.println("Error occured when reading from properties!");
		}
	}

	/**
	 * Method used for initialization of properties.
	 * 
	 * @param path path
	 * @throws IOException
	 */
	private void initProperties(Path path) throws IOException {
		Properties properties = new Properties();
		properties.load(Files.newInputStream(path));

		// initialize single properties
		address = properties.getProperty("server.address");
		domainName = properties.getProperty("server.domainName");
		port = Integer.parseInt(properties.getProperty("server.port"));
		workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
		documentRoot = Paths.get(properties.getProperty("server.documentRoot")).toAbsolutePath();

		// initialize mime types
		String mimeConfig = properties.getProperty("server.mimeConfig");
		Properties mimeProperties = new Properties();
		mimeProperties.load(Files.newInputStream(Paths.get(mimeConfig)));
		mimeProperties.entrySet().forEach(m -> mimeTypes.put((String) m.getKey(), (String) m.getValue()));

		// initialize workers
		String workersConfig = properties.getProperty("server.workers");
		Properties workerProperties = new Properties();
		workerProperties.load(Files.newInputStream(Paths.get(workersConfig)));
		workerProperties.entrySet().forEach(w -> {
			String pathw = (String) w.getKey();
			String fqcn = (String) w.getValue();
			Class<?> referenceToClass = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(pathw, iww);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Starts server thread if not already running.
	 */
	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
			memoryCleaner.start();
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
		System.out.println("Server is open.");
	}

	/**
	 * Signals server thread to stop running.
	 */
	protected synchronized void stop() {
		threadPool.shutdown();
		stopServer = true;
		System.out.println("Server is closed.");
	}

	/**
	 * Model of server thread that extends {@link Thread}. It runs whole server.
	 * 
	 * @author dbrcina
	 *
	 */
	protected class ServerThread extends Thread {
		private static final int SOCKET_TIMEOUT = 20000;
		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket()) {
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				serverSocket.setSoTimeout(SOCKET_TIMEOUT);
				while (true) {
					if (stopServer) {
						break;
					}
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * An implementation of daemonic thread that is used to clean memory from
	 * expired sessions to avoid excessive memory consumption.
	 * 
	 * @author dbrcina
	 *
	 */
	private class MemoryCleaner extends Thread {
		/*
		 * Interval which this thread uses for cleaning memory.
		 */
		private static final int INTERVAL = 10000 * 50;

		/**
		 * Default constructor.
		 */
		public MemoryCleaner() {
			setDaemon(true);
		}

		@Override
		public void run() {
			while (true) {
				if (stopServer) {
					break;
				}
				try {
					Thread.sleep(INTERVAL);
				} catch (InterruptedException ignored) {
				}
				sessions.entrySet().removeIf(e -> e.getValue().validUntil < System.currentTimeMillis());
			}
		}
	}

	/**
	 * An implementation of client worker.
	 * 
	 * @author dbrcina
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/**
		 * CLient socket.
		 */
		private Socket csocket;
		/**
		 * Input stream.
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream.
		 */
		private OutputStream ostream;
		/**
		 * Protocol version.
		 */
		private String version;
		/**
		 * Protocol method.
		 */
		private String method;
		/**
		 * Name of the host.
		 */
		private String host;
		/**
		 * Map of parameters.
		 */
		private Map<String, String> params = new HashMap<>();
		/**
		 * Map of temporary parameters.
		 */
		private Map<String, String> tempParams = new HashMap<>();
		/**
		 * Map of persistent parameters.
		 */
		private Map<String, String> permParams = new HashMap<>();
		/**
		 * List of cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<>();
		/**
		 * Session ID.
		 */
		private String SID;
		/**
		 * Reference of {@link RequestContext}.
		 */
		private RequestContext requestContext;

		/**
		 * Constructor used for initialization.
		 * 
		 * @param csocket client socket.
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> headers = validateHeaders();
				initHost(headers);
				checkSession(headers);
				String path = extractPath(headers);
				internalDispatchRequest(path, true);
			} catch (Exception e) {
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Validates session.
		 * 
		 * @param headers header.
		 */
		private void checkSession(List<String> headers) {
			List<String> cookiesHeader = headers.stream().filter(s -> s.startsWith("Cookie"))
					.collect(Collectors.toList());
			String sidCandidate = null;
			for (String cookie : cookiesHeader) {
				String cookieArguments = cookie.substring(cookie.indexOf(" ") + 1);
				String[] argument = cookieArguments.split(";");
				String[] firstArg = argument[0].split("=");
				if (firstArg[0].equals("sid")) {
					sidCandidate = firstArg[1].replace("\"", "");
				}
			}
			checkSIDCandidate(sidCandidate);
		}

		/**
		 * Validates <code>sidCandiate</code> session.
		 * 
		 * @param sidCandidate candidate.
		 */
		private void checkSIDCandidate(String sidCandidate) {
			if (sidCandidate == null) {
				createNewSession();
				return;
			}
			SessionMapEntry entry = sessions.get(sidCandidate);
			if (entry == null || !entry.host.equals(host)) {
				createNewSession();
				return;
			}
			if (entry.validUntil < System.currentTimeMillis()) {
				sessions.remove(sidCandidate);
				createNewSession();
				return;
			}
			entry.sid = sidCandidate;
			SID = sidCandidate;
			entry.validUntil = sessionTimeout * 1000 + System.currentTimeMillis();
			permParams = entry.map;
		}

		/**
		 * Creates new instance of {@link SessionMapEntry}.
		 */
		private void createNewSession() {
			SID = generateSID();
			SessionMapEntry entry = new SessionMapEntry(SID, host, System.currentTimeMillis() + sessionTimeout * 1000);
			sessions.put(SID, entry);
			permParams = entry.map;
			outputCookies.add(new RCCookie("sid", SID, host, "/", null));
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Delegetates factory methods as determined by <code>urlPath</code>.
		 * 
		 * @param urlPath    path.
		 * @param directCall direct call.
		 * @throws Exception if something goes wrong.
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (urlPath.startsWith("/private") && directCall) {
				sendError(ostream, 404, "Cannot refere to private!");
				return;
			}
			if (requestContext == null) {
				requestContext = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			}
			if (urlPath.startsWith("/ext/")) {
				extWorkers(urlPath);
				return;
			}
			if (workersJob(urlPath)) {
				return;
			}

			Path fullPath = validateFullPath(urlPath);
			if (fullPath == null) {
				return;
			}
			String pathName = fullPath.getFileName().toString();
			if (pathName.endsWith(".smscr")) {
				executeScript(fullPath);
			} else {
				writeRegularFile(pathName, fullPath);
			}
		}

		/**
		 * Writes regural file to client's stream.
		 * 
		 * @param pathName path name.
		 * @param fullPath path.
		 * @throws IOException
		 */
		private void writeRegularFile(String pathName, Path fullPath) throws IOException {
			String mime = determineMimeType(pathName.substring(pathName.indexOf(".") + 1));
			requestContext.setMimeType(mime);
			try (InputStream is = new BufferedInputStream(Files.newInputStream(fullPath))) {
				byte[] buf = new byte[1024];
				while (true) {
					int r = is.read(buf);
					if (r < 0) {
						break;
					}
					requestContext.write(buf, 0, r);
				}
			}
		}

		/**
		 * Validates full path of <code>urlPath</code>
		 * 
		 * @param urlPath path.
		 * @return new instance of {@link Path}, or null if error occures.
		 * @throws IOException
		 */
		private Path validateFullPath(String urlPath) throws IOException {
			Path fullPath = documentRoot.resolve(urlPath.substring(1));
			if (!fullPath.startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbidden");
				return null;
			}
			if (!Files.exists(fullPath) || !Files.isRegularFile(fullPath) || !Files.isReadable(fullPath)) {
				sendError(ostream, 404, "Error");
				return null;
			}
			return fullPath;
		}

		/**
		 * Do the job as determined by <code>urlPath</code>.
		 * 
		 * @param urlPath path.
		 * @return <code>true</code> if work was done, otherwise <code>false</code>.
		 * @throws Exception
		 */
		private boolean workersJob(String urlPath) throws Exception {
			IWebWorker iww = workersMap.get(urlPath);
			if (iww != null) {
				iww.processRequest(requestContext);
				return true;
			}
			return false;
		}

		/**
		 * Helper method used for validation of headers. If something is invalid like
		 * method name, version name or similar, <code>null</code> is returned and error
		 * message is sent to client's stream.
		 * 
		 * @return list of strings representing each row in headers part, otherwise
		 *         <code>null</code>.
		 * @throws IOException if something goes wrong when reading/writing from/to
		 *                     stream.
		 */
		private List<String> validateHeaders() throws IOException {
			byte[] request = readRequest(istream);
			if (request == null) {
				sendError(ostream, 400, "Bad request");
				return null;
			}
			List<String> headers = extractHeaders(new String(request, StandardCharsets.US_ASCII));
			String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
			if (!validateFirstLine(firstLine)) {
				sendError(ostream, 400, "Bad request");
				return null;
			}
			return headers;
		}

		/**
		 * Method used for extracting requested path into legit path and parameters (if
		 * provided).
		 * 
		 * @param headers headers.
		 * @return path.
		 * @throws IOException if something goes wrong while writing to stream with
		 *                     {@link #sendError(OutputStream, int, String)} method.
		 */
		private String extractPath(List<String> headers) throws IOException {
			String requestedPath = headers.get(0).split(" ")[1];
			String[] parts = requestedPath.split("\\?");
			if (parts.length != 1) {
				parseParameters(parts[1]);
			}
			return parts[0];
		}

		/**
		 * Method that starts worker defined in <code>urlPath</code>.<br>
		 * For example:<br>
		 * <code>www.localhost.com:5721/ext/HelloWorker</code> url defines one
		 * {@link HelloWorker} worker.<br>
		 * <code>www.localhost.com:5721/ext/EchoParams?name1=value1&name2=value2&...&namen=valuen</code>
		 * defines {@link EchoParams} worker that takes arbitrary number of parameters
		 * etc.
		 * 
		 * @param urlPath url path.
		 * @throws Exception if something goes wrong whilst workers work.
		 */
		private void extWorkers(String urlPath) throws Exception {
			String path = urlPath.substring(5);
			String fqcn = "hr.fer.zemris.java.webserver.workers.";
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn + path);
			@SuppressWarnings("deprecation")
			Object newObject = referenceToClass.newInstance();
			IWebWorker iww = (IWebWorker) newObject;
			iww.processRequest(requestContext);
		}

		/**
		 * Executes provided <code>script</code> through {@link SmartScriptEngine}.
		 * 
		 * @param script path to some script
		 * @throws IOException if something happens while reading from a file.
		 */
		private void executeScript(Path script) throws IOException {
			String documentBody = new String(Files.readAllBytes(script), StandardCharsets.UTF_8);
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
					new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID)).execute();
		}

		/**
		 * Determines mime type from <code>extension</code>.
		 * 
		 * @param extension extension.
		 * @return mime type.
		 */
		private String determineMimeType(String extension) {
			String mime = mimeTypes.get(extension);
			if (mime == null) {
				return "application/octet-stream";
			}
			return mime;
		}

		/**
		 * Parses given <code>paramString</code> into arguments that are divided by
		 * <code>&</code> sign.
		 * 
		 * @param paramString params.
		 * @throws IOException if something goes wrong while writing to stream.
		 */
		private void parseParameters(String paramString) throws IOException {
			List<String> list = Arrays.asList(paramString.split("&"));
			for (String s : list) {
				String[] args = s.split("=");
				if (args.length % 2 == 1) {
					sendError(ostream, 404, "Invalid parameters");
					return;
				}
				params.put(args[0], args[1]);
			}
		}

		/**
		 * Initialization of host.
		 * 
		 * @param headers headers.
		 */
		private void initHost(List<String> headers) {
			Optional<String> optHost = headers.stream().filter(s -> s.startsWith("Host: ")).findFirst();
			if (optHost.isPresent()) {
				String host = optHost.get();
				host = host.substring(host.indexOf(" ") + 1).trim();
				int indexOfColon = host.indexOf(":");
				if (indexOfColon != -1) {
					host = host.substring(0, indexOfColon);
				}
				this.host = host;
			} else {
				this.host = domainName;
			}
		}

		/**
		 * Validation of first line.
		 * 
		 * @param firstLine first line.
		 * @return <code>true</code> if line is valid, otherwise <code>false</code>.
		 */
		private boolean validateFirstLine(String[] firstLine) {
			if (firstLine == null || firstLine.length != 3) {
				return false;
			}
			this.method = firstLine[0].toUpperCase();
			this.version = firstLine[2].toUpperCase();
			return method.equals("GET") & (version.equalsIgnoreCase("HTTP/1.0") || version.equals("HTTP/1.1"));
		}

		/**
		 * Reads request from {@link InputStream} <code>is</code>.
		 * 
		 * @param is input stream.
		 * @return byte array.
		 * @throws IOException
		 */
		private byte[] readRequest(InputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Extract headers by <code>\n</code> sign.
		 * 
		 * @param requestHeader header.
		 * @return list of header elements.
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Writes error header to {@link OutputStream} <code>cos</code>.
		 * 
		 * @param cos        client output stream.
		 * @param statusCode status code.
		 * @param statusText status text.
		 * @throws IOException if something goes wrong then writing to <code>cos</code>.
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {
			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();
		}

	}

	/**
	 * Model of one session entry.
	 * 
	 * @author dbrcina
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Session id.
		 */
		@SuppressWarnings("unused")
		private String sid;
		/**
		 * Session host.
		 */
		private String host;
		/**
		 * Life span.
		 */
		private long validUntil;
		/**
		 * Map of params.
		 */
		private Map<String, String> map;

		/**
		 * Constructor used for initialization.
		 * 
		 * @param sid        sid.
		 * @param host       host.
		 * @param validUntil valid unti.
		 */
		public SessionMapEntry(String sid, String host, long validUntil) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = new ConcurrentHashMap<>();
		}

	}

	/**
	 * Generates random session id.
	 * 
	 * @return session id.
	 */
	private String generateSID() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SID_LENGTH; i++) {
			sb.append((char) (sessionRandom.nextInt('Z' - 'A') + 'A'));
		}
		return sb.toString();
	}

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Program expected one argument, path to some file!");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
		System.out.println("Enter exit to stop the server");
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				if (sc.nextLine().equals("exit")) {
					server.stop();
					System.out.println("Thread will \"die\" eventually..");
					break;
				}
			}
		}
	}
}
