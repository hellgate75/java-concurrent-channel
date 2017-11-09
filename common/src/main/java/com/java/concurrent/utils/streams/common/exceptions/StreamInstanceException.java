/**
 * 
 */
package com.java.concurrent.utils.streams.common.exceptions;

/**
 * Exception thrown during Stream instance and/or Registry opt-in
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class StreamInstanceException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 6744378336188957181L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public StreamInstanceException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public StreamInstanceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public StreamInstanceException(String message, Throwable cause) {
		super(message, cause);
	}

}
