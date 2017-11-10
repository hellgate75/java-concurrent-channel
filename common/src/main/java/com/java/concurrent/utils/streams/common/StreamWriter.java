/**
 * 
 */
package com.java.concurrent.utils.streams.common;

import java.util.Collection;

import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;
import com.java.concurrent.utils.streams.common.exceptions.StreamNullableAssignementException;

/**
 * Interface that determines Stream Reader default behaviors
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * @param <T> Stream Output Type
 * @see AutoCloseable
 *
 */
public interface StreamWriter<T> extends AutoCloseable {

	/**
	 * Write Single Stream Element
	 * @param t Element instance to write to the stream
	 * @return write status
	 * @throws StreamNullableAssignementException Thrown during Stream read operation if the element is null
	 * @throws StreamIOException Thrown during Stream write operation
	 */
	boolean write(T t) throws StreamNullableAssignementException, StreamIOException;

	/**
	 * Write Stream Element Array
	 * @param t Element array to write to the stream
	 * @return number of written elements
	 * @throws StreamNullableAssignementException Thrown during Stream read operation if the element array is null
	 * @throws StreamIOException Thrown during Stream write operation
	 */
	long write(@SuppressWarnings("unchecked") T ...t) throws StreamNullableAssignementException, StreamIOException;

	/**
	 * Write Stream Element Collection
	 * @param t Element collection to write to the stream
	 * @return number of written elements
	 * @throws StreamNullableAssignementException Thrown during Stream read operation if the element collection is null
	 * @throws StreamIOException Thrown during Stream write operation
	 */
	long write(Collection<T> t) throws StreamNullableAssignementException, StreamIOException;

	/**
	 * Close Stream
	 * @throws StreamIOException Thrown when trying to close Stream
	 */
	void close() throws StreamIOException;

	/**
	 * Verify is Stream is open
	 * @return state of stream
	 */
	boolean isOpen();
	
}
