/**
 * 
 */
package com.java.concurrent.utils.streams.channel.listeners;

/**
 * Interface that define behavior of Queue New Element Insert
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @param <T> Queue Listening Type
 */
public interface ChannelOutputListener<T> {
	/**
	 * Listen on Consume of single element
	 * @param t Consumed element
	 */
	void consumed(T t);
	/**
	 * Listen on Consume of array of elements
	 * @param t Consumed elements
	 */
	@SuppressWarnings("unchecked")
	void consumed(T ...t);
}
