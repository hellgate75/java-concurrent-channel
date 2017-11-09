/**
 * 
 */
package com.java.concurrent.utils.streams.channel.exceptions;

/**
 * Exception thrown during Channel instance and/or Registry opt-in
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class ChannelInstanceException extends Exception {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 6744378336188957181L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public ChannelInstanceException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause Exception throwing clause
	 */
	public ChannelInstanceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 * @param cause Exception throwing clause
	 */
	public ChannelInstanceException(String message, Throwable cause) {
		super(message, cause);
	}

}
