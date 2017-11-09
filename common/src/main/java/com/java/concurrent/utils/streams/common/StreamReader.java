/**
 * 
 */
package com.java.concurrent.utils.streams.common;

import java.util.List;

import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;

/**
 * Interface that determines Stream Reader default behaviors
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * @param <T> Stream Input Type
 * @see AutoCloseable
 */
public interface StreamReader<T> extends AutoCloseable {

	/**
	 * Read Single Stream Element
	 * @return single stream element
	 * @throws StreamIOException Thrown during Stream read operation
	 */
	T read() throws StreamIOException;

	/**
	 * Read All Available Stream Elements
	 * @return all available stream elements
	 * @throws StreamIOException Thrown during Stream read operation
	 */
	List<T> readAll() throws StreamIOException;

	/**
	 * Verify if Stream is empty
	 * @return Stream empty state
	 */
	boolean isEmpty();

	/**
	 * Open Stream
	 */
	void open() throws StreamIOException;

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
