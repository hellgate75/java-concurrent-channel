/**
 * 
 */
package com.java.concurrent.streams.channel.operators;

/**
 * Channel filter  behavior descriptor, it is used to filter wanted and unwanted object served inqueue
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * 
 * @param <T> Consume Type
 */
public interface ChannelFilter<T> {
	boolean accept(T t);
}
