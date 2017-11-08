/**
 * 
 */
package com.java.concurrent.streams.channel.operators;

import java.util.Collection;

import com.java.concurrent.streams.channel.exceptions.ChannelIOException;
import com.java.concurrent.streams.channel.exceptions.ChannelNullableAssignementException;

/**
 * Channel Producer implemented behavior
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @param <T> Produced Type
 */
public interface ChannelProducer<T> {
	
	/**
	 * Add a single value to Channel
	 * @param t single value
	 * @return insert status
	 * @throws ChannelNullableAssignementException Thrown when element is null
	 * @throws ChannelIOException Thrown when errors occurs during insert operations
	 */
	boolean add(T t) throws ChannelNullableAssignementException, ChannelIOException;

	/**
	 * Add an array of values to Channel
	 * @param t array of values
	 * @return number of added elements
	 * @throws ChannelNullableAssignementException Thrown when array is null
	 * @throws ChannelIOException Thrown when errors occurs during insert operations
	 */
	@SuppressWarnings("unchecked")
	long add(T ...t) throws ChannelNullableAssignementException, ChannelIOException;

	/**
	 * Add a collection of values to Channel
	 * @param collection collection of values
	 * @return number of added elements
	 * @throws ChannelNullableAssignementException Thrown when collection is null
	 * @throws ChannelIOException Thrown when errors occurs during insert operations
	 */
	long add(Collection<T> collection) throws ChannelNullableAssignementException, ChannelIOException;
}
