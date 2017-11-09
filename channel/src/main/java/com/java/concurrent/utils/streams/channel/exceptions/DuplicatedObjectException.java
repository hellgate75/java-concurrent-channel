/**
 * 
 */
package com.java.concurrent.utils.streams.channel.exceptions;

/**
 * Exception thrown during Channel Registry opt-in, and provided Channel name is already in use 
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class DuplicatedObjectException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 8731299673833514267L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public DuplicatedObjectException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public DuplicatedObjectException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public DuplicatedObjectException(String message, Throwable cause) {
		super(message, cause);
	}

}
