/**
 * 
 */
package com.java.concurrent.utils.streams.common.exceptions;

/**
 * Exception thrown during Stream operations, when using features not yet implemented or 
 * suspended for review
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class StreamOperationNotPermittedException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 181301037599078469L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public StreamOperationNotPermittedException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public StreamOperationNotPermittedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public StreamOperationNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}

}
