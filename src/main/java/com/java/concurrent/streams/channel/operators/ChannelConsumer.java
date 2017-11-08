/**
 * 
 */
package com.java.concurrent.streams.channel.operators;

/**
 * Consumer behavior descriptor
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * 
 * @param <T> Consume Type
 */
public interface ChannelConsumer<T> {
	void consume(T t);
}
