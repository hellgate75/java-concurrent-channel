/**
 * 
 */
package com.java.concurrent.utils.streams.common.behaviors;

/**
 * Stream filter  behavior descriptor, it is used to filter wanted and unwanted object saved in Stream
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * 
 * @param <T> Filter Type
 */
public interface StreamFilter<T> {
	/**
	 * Filter accepting or not accepting (rejecting) an item, before it is added to Stream
	 * @param t Element to accept or reject
	 * @return return accept state
	 */
	boolean accept(T t);
}
