/**
 * 
 */
package com.java.concurrent.utils.streams.common.exceptions;

/**
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class MaxNumberOfErrorsReachedException extends RuntimeException {

	/**
	 * Exception Serial Version ID
	 */
	private static final long serialVersionUID = 2737067142089979469L;

	/**
	 * Constructor
	 * @param expected
	 * @param effective
	 */
	public MaxNumberOfErrorsReachedException(long expected, long effective) {
		super("Number of Errors: " + effective + " overcame limit: " + expected);
	}

}
