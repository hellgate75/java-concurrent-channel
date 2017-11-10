/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.behaviors;

import com.java.concurrent.utils.streams.common.behaviors.Mapper;

/**
 * Aggregate a string content to build an Item
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
  *
 * @param <I> Input Line String Type
 * @param <O> Output Item Type
 * @see Mapper
 */
public interface LineMapper<I extends String, O> extends Mapper<I, O> {
	default long getMaxNumberOfReadErrors() {
		return -1L;
	}
}
