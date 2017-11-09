/**
 * 
 */
package com.java.concurrent.utils.streams.common.exceptions;

/**
 * Exception thrown during Stream read write operations
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class StreamIOException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 6747025107409224604L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public StreamIOException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public StreamIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public StreamIOException(String message, Throwable cause) {
		super(message, cause);
	}

}
