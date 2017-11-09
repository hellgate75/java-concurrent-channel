/**
 * 
 */
package com.java.concurrent.utils.streams.common.behaviors;

/**
 * Consumer behavior descriptor
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * 
 * @param <T> Consume Type
 */
public interface StreamConsumer<T> {
	/**
	 * Consumer elements from Channel
	 * @param t Element to be consumed
	 */
	void consume(T t);
}
