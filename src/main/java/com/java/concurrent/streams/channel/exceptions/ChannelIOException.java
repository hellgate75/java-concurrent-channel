/**
 * 
 */
package com.java.concurrent.streams.channel.exceptions;

/**
 * Exception thrown during Channel read write operations
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class ChannelIOException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 6747025107409224604L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public ChannelIOException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public ChannelIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public ChannelIOException(String message, Throwable cause) {
		super(message, cause);
	}

}
