/**
 * 
 */
package com.java.concurrent.streams.channel.operators;

/**
 * Channel filter  behavior descriptor, it is used to filter wanted and unwanted object saved in Channel
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * 
 * @param <T> Consume Type
 */
public interface ChannelFilter<T> {
	/**
	 * Filter accepting or not accepting (rejecting) an item, before it is added to Channel
	 * @param t Element to accept or reject
	 * @return return accept state
	 */
	boolean accept(T t);
}
