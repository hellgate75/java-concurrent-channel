/**
 * 
 */
package com.java.concurrent.utils.streams.channel.exceptions;

/**
 * Exception thrown during Channel operations, when using features not yet implemented or 
 * suspended for review
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class ChannelOperationNotPermittedException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 181301037599078469L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public ChannelOperationNotPermittedException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public ChannelOperationNotPermittedException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public ChannelOperationNotPermittedException(String message, Throwable cause) {
		super(message, cause);
	}

}
