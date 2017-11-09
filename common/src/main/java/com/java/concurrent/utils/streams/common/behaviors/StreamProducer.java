/**
 * 
 */
package com.java.concurrent.utils.streams.common.behaviors;

import java.util.Collection;

import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;
import com.java.concurrent.utils.streams.common.exceptions.StreamNullableAssignementException;

/**
 * Stream Producer behavior
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @param <T> Produced Type
 */
public interface StreamProducer<T> {
	
	/**
	 * Add a single value to Stream
	 * @param t single value
	 * @return insert status
	 * @throws StreamNullableAssignementException Thrown when element is null
	 * @throws StreamIOException Thrown when errors occurs during insert operations
	 */
	boolean add(T t) throws StreamNullableAssignementException, StreamIOException;

	/**
	 * Add an array of values to Stream
	 * @param t array of values
	 * @return number of added elements
	 * @throws StreamNullableAssignementException Thrown when array is null
	 * @throws StreamIOException Thrown when errors occurs during insert operations
	 */
	@SuppressWarnings("unchecked")
	long add(T ...t) throws StreamNullableAssignementException, StreamIOException;

	/**
	 * Add a collection of values to Stream
	 * @param collection collection of values
	 * @return number of added elements
	 * @throws StreamNullableAssignementException Thrown when collection is null
	 * @throws StreamIOException Thrown when errors occurs during insert operations
	 */
	long add(Collection<T> collection) throws StreamNullableAssignementException, StreamIOException;
}
