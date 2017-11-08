/**
 * 
 */
package com.java.concurrent.streams.channel.exceptions;

/**
 * Exception thrown during Channel assignament operations, when at least one of operand is nullable or empty
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class ChannelNullableAssignementException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 4433972168635873198L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public ChannelNullableAssignementException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public ChannelNullableAssignementException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public ChannelNullableAssignementException(String message, Throwable cause) {
		super(message, cause);
	}

}
