/**
 * 
 */
package com.java.concurrent.streams.channel.listener;

/**
 * Interface that define behavior of Queue New Element Insert
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @param <T> Queue Listening Type
 */
public interface ChannelInputListener<T> {
	/**
	 * Accepted element method, it is called at any time a new item has been accepted in the queue
	 * @param t Accepted Parameter
	 */
	void accepted(T t);
	/**
	 * Reject element method, it is called at any time a new item has been reject and not inserted in the queue
	 * @param t Rejected Parameter
	 */
	void rejected(T t);
}
