/**
 * 
 */
package com.java.concurrent.utils.streams.common.exceptions;

/**
 * Exception thrown during Stream assignment operations, when at least one of operand is nullable or empty
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class StreamNullableAssignementException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 4433972168635873198L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public StreamNullableAssignementException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public StreamNullableAssignementException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public StreamNullableAssignementException(String message, Throwable cause) {
		super(message, cause);
	}

}
