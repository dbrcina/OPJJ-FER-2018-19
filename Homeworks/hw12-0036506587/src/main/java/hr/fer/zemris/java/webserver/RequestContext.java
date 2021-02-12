package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class models a request context. It can store multiple parameters, create
 * http header and write to {@link OutputStream} . It consists of many private
 * properties divided into <i>read-only</i> and <i>write-only</i> or both. Maps
 * {@link #persistentParameters} and {@link #temporaryParameters} are both
 * writeable and readable for example.
 * 
 * <p>
 * <i>Write-only</i> properties are:
 * <ul>
 * <li>{@link #encoding}</li>
 * <li>{@link #statusCode}</li>
 * <li>{@link #statusText}</li>
 * <li>{@link #mimeType}</li>
 * <li>{@link #contentLength}</li>
 * <li>Add new values into {@link #outputCookies}</li>
 * </ul>
 * </p>
 * 
 * @author dbrcina
 *
 */
public class RequestContext {

	/**
	 * Constant representing message that is thrown when used tries to modify some
	 * <i>write-only</i> properties after creation of header.
	 */
	private static final String MODIFICATION_MSG = "Header has been generated and modifications are not allowed!";

	/*
	 * ....DEFAULT PROPERTY VALUES....
	 */
	/**
	 * Constant representing default encoding.
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * Constant representing default status code.
	 */
	private static final int DEFAULT_STATUS_CODE = 200;
	/**
	 * Constant representing default status text.
	 */
	private static final String DEFAULT_STATUS_TEXT = "OK";
	/**
	 * Constant representing default mime type.
	 */
	private static final String DEFAULT_MIME_TYPE = "text/html";
	/**
	 * Constant representing default content length.
	 */
	private static final Long DEFAULT_CONTENT_LENGTH = null;

	/*
	 * ....PRIVATE PROPERTIES....
	 */
	/**
	 * An instance of {@link OutputStream}.
	 */
	private OutputStream outputStream;
	/**
	 * An instance of {@link Charset}.
	 */
	private Charset charset;

	private String SID;
	/**
	 * Boolean flag which tells whether header is generated.
	 */
	private boolean headerGenerated;
	/**
	 * 
	 */
	private IDispatcher dispatcher;
	/*
	 * ....WRITE-ONLY PROPERTIES....
	 */
	/**
	 * {@link String} value used for encoding.
	 */
	private String encoding = DEFAULT_ENCODING;
	/**
	 * Value of status code.
	 */
	private int statusCode = DEFAULT_STATUS_CODE;
	/**
	 * Representation of status text.
	 */
	private String statusText = DEFAULT_STATUS_TEXT;
	/**
	 * Representation of mime type.
	 */
	private String mimeType = DEFAULT_MIME_TYPE;
	/**
	 * {@link Long} value of content length.
	 */
	private Long contentLength = DEFAULT_CONTENT_LENGTH;

	/*
	 * ....PRIVATE COLLECTIONS....
	 */
	/**
	 * {@link Map} used for parameters. It is a <code>read-only</code> map. Keys are
	 * parameter names and values are parameters.
	 */
	private Map<String, String> parameters;
	/**
	 * {@link Map} used for temporary parameters. Keys are parameter names and
	 * values are parameters.
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * {@link Map} used for persistentParameters. Keys are parameter names and
	 * values are parameters.
	 */
	private Map<String, String> persistentParameters;
	/**
	 * {@link List} used for instances of {@link RCCookie}.
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Constructor used for initialization. <code>outputStream</code> cannot be
	 * <code>null</code>. If any of collections are <code>null-references</code>,
	 * they are treated as an empty collection.
	 * 
	 * @param outputStream         output stream.
	 * @param parameters           parameters.
	 * @param persistentParameters persistent parameters.
	 * @param outputCookies        cookies.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {

		this(outputStream, persistentParameters, persistentParameters, outputCookies, null, null, null);
	}

	/**
	 * Constructor used for initialization. <code>outputStream</code> cannot be
	 * <code>null</code>. If any of collections are <code>null-references</code>,
	 * they are treated as an empty collection.
	 * 
	 * @param outputStream         output stream.
	 * @param parameters           parameters.
	 * @param persistentParameters persistent parameters.
	 * @param outputCookies        cookies.
	 * @param temporaryParameters  temporary parameters.
	 * @param dispatcher           dispatcher.
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String SID) {

		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.dispatcher = dispatcher;
		this.SID = SID;
	}

	/*
	 * ....WRITE METHODS....
	 */
	/**
	 * Writes <code>data</code> byte array into {@link OutputStream}.
	 * 
	 * @param data byte array.
	 * @return this context.
	 * @throws IOException if something happens while writing into file.
	 * @see OutputStream#write(byte[])
	 */
	public RequestContext write(byte[] data) throws IOException {
		return write(data, 0, data.length);
	}

	/**
	 * Writes <code>data</code> byte array into {@link OutputStream} from position
	 * <code>offset</code> for <code>len</code> spaces.
	 * 
	 * @param data   byte array.
	 * @param offset offset from start.
	 * @param len    len.
	 * @return this context.
	 * @throws IOException if something happens while writing into file.
	 * @see OutputStream#write(byte[], int, int)
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}

	/**
	 * Writes <code>text</code> into {@link OutputStream}. <code>text</code> is
	 * interpreted as a byte array.
	 * 
	 * @param text input text.
	 * @return this context.
	 * @throws IOException if something happens while writing into file.
	 * @see String#getBytes(Charset)
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(text.getBytes(charset));
		outputStream.flush();
		return this;
	}

	/*
	 * ....PUBLIC GETTERS....
	 */
	/**
	 * Getter for parameter from {@link #parameters} as determined by given key
	 * <code>name</code>.
	 * 
	 * @param name parameter name.
	 * @return parameter or <code>null</code> if no association exists.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Getter for all parameter names as instance of {@link Set}.
	 * 
	 * @return unmodifiable set of parameter names.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Getter for persistent parameter from {@link #persistentParameters} as
	 * determined by given key <code>name</code>.
	 * 
	 * @param name parameter name.
	 * @return persistent parameter or <code>null</code> if no association exists.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Getter for all persistent parameter names as instance of {@link Set}.
	 * 
	 * @return unmodifiable set of persistent parameter names.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Getter for temporary parameter from {@link #temporaryParameters} as
	 * determined by given key <code>name</code>.
	 * 
	 * @param name temporary parameter name.
	 * @return temporary parameter or <code>null</code> if no association exists.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Getter for all temporary parameter names as instance of {@link Set}.
	 * 
	 * @return set of parameter names.
	 */
	public Set<String> getTemporaryParameterNames() {
		return temporaryParameters.keySet();
	}

	/**
	 * Getter for session ID, an unique identifier.
	 * 
	 * @return session ID.
	 */
	public String getSessionID() {
		return SID;
	}

	/**
	 * Getter for parameters.
	 * 
	 * @return parameters.
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Getter for temporary parameters.
	 * 
	 * @return temporary parameters.
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Getter for persistent parameters.
	 * 
	 * @return parameters.
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Getter for dispatcher.
	 * 
	 * @return dispatcher.
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/*
	 * ....PUBLIC SETTERS....
	 */
	/**
	 * Stores a <code>value</code> to {@link #persistentParameters} map.
	 * 
	 * @param name  key.
	 * @param value value.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Stores <code>value</code> to {@link #temporaryParameters} map as determined
	 * by key <code>name</code>.
	 * 
	 * @param name  key.
	 * @param value value.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Setter for encoding.
	 * 
	 * @param encoding encoding.
	 * @throws RuntimeException if this method is called after creation of header.
	 */
	public void setEncoding(String encoding) {
		checkIfHeaderExists();
		this.encoding = encoding;
	}

	/**
	 * Setter for status code.
	 * 
	 * @param statusCode status code.
	 * @throws RuntimeException if this method is called after creation of header.
	 */
	public void setStatusCode(int statusCode) {
		checkIfHeaderExists();
		this.statusCode = statusCode;
	}

	/**
	 * Setter for status text.
	 * 
	 * @param statusText status text.
	 * @throws RuntimeException if this method is called after creation of header.
	 */
	public void setStatusText(String statusText) {
		checkIfHeaderExists();
		this.statusText = statusText;
	}

	/**
	 * Setter for mime type.
	 * 
	 * @param mimeType mime type.
	 * @throws RuntimeException if this method is called after creation of header.
	 */
	public void setMimeType(String mimeType) {
		checkIfHeaderExists();
		this.mimeType = mimeType;
	}

	/**
	 * Setter for content length.
	 * 
	 * @param contentLength content length.
	 * @throws RuntimeException if this method is called after creation of header.
	 */
	public void setContentLength(Long contentLength) {
		checkIfHeaderExists();
		this.contentLength = contentLength;
	}

	/*
	 * ....PUBLIC COLLECTION METHODS....
	 */
	/**
	 * Adds <code>cookie</code> into {@link #outputCookies} list.
	 * 
	 * @param cookie cookie.
	 * @throws RuntimeException if this method is called after creation of header.
	 */
	public void addRCCookie(RCCookie cookie) {
		checkIfHeaderExists();
		outputCookies.add(cookie);
	}

	/**
	 * Removes a value from {@link #persistentParameters} map as determined by key
	 * <code>name</code>.
	 * 
	 * @param name key.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Removes a value from {@link #temporaryParameters} map as determined by key
	 * <code>name</code>.
	 * 
	 * @param name key.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/*
	 * ....PRIVATE HELPER METHODS....
	 */
	/**
	 * Helper method used for creation of http header. After header is created,
	 * {@link #headerGenerated} is set to <code>true</code>.
	 * 
	 * @throws IOException if something happens while writing into file.
	 * @see OutputStream#write(byte[])
	 */
	private void generateHeader() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType + (mimeType.startsWith("text") ? "; charset=" + encoding : "") + "\r\n");
		sb.append(contentLength != null ? "Content-Length: " + contentLength + "\r\n" : "");

		if (!outputCookies.isEmpty()) {
			outputCookies.forEach(c -> {
				sb.append("Set-Cookie: ");
				sb.append(c.getName() + "=" + "\"" + c.getValue() + "\"; ");
				sb.append(c.getDomain() != null ? "Domain=" + c.getDomain() + "; " : "");
				sb.append(c.getPath() != null ? "Path=" + c.getPath() + "; " : "");
				sb.append(c.getMaxAge() != null ? "Max-Age=" + c.getMaxAge() + "; " : "");
				sb.append("HttpOnly");
				sb.append("\r\n");
			});
		}

		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		outputStream.flush();

		charset = Charset.forName(encoding);
		headerGenerated = true;
	}

	/**
	 * Helper method which checks whether header is generated. It throws
	 * {@link RuntimeException} if it is generated.
	 */
	private void checkIfHeaderExists() {
		if (headerGenerated) {
			throw new RuntimeException(MODIFICATION_MSG);
		}
	}

	/**
	 * Model of web cookie. It provides four <i>read-only</i> properties.
	 * 
	 * <pre>
	 * {@link #name}
	 * {@link #value}
	 * {@link #domain}
	 * {@link #path}
	 * {@link #maxAge}
	 * </pre>
	 * 
	 * @author dbrcina
	 *
	 */
	public static class RCCookie {
		/**
		 * Cookie's name.
		 */
		private String name;
		/**
		 * Cookie's value.
		 */
		private String value;
		/**
		 * Cookie's domain.
		 */
		private String domain;
		/**
		 * Cookie's path.
		 */
		private String path;
		/**
		 * Cookie's max age.
		 */
		private Integer maxAge;

		/**
		 * Constructor used for initialization.
		 * 
		 * @param name   name.
		 * @param value  value.
		 * @param domain domain.
		 * @param path   path.
		 * @param maxAge max age.
		 */
		public RCCookie(String name, String value, String domain, String path, Integer maxAge) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Getter for name property.
		 * 
		 * @return cookie's name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for value property.
		 * 
		 * @return cookie's value.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter for domain property.
		 * 
		 * @return cookie's domain.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter for path property.
		 * 
		 * @return cookie's path.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter for max age property.
		 * 
		 * @return cookie's max age.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}
}
