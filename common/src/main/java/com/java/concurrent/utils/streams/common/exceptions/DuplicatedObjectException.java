/**
 * 
 */
package com.java.concurrent.utils.streams.common.exceptions;

/**
 * Exception thrown during opt-in or creation operations, and provided element already apted-in 
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
