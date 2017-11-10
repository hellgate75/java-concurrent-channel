/**
 * 
 */
package com.java.concurrent.utils.streams.common.behaviors;

import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;

/**
 * Interface that describes behavior of Type Transformation from Type I to Type O 
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @param <I> Input Type
 * @param <O> Output Type
 */
public interface Mapper<I, O> {
	/**
	 * Map type I in type O
	 * @param i input element to be mapped to type O
	 * @return mapped type O instance
	 * @throws StreamIOException Thrown when any exception occurs during mapping operation
	 */
	O map(I i) throws StreamIOException;
}
