/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.behaviors;

import com.java.concurrent.utils.streams.common.behaviors.Mapper;

/**
 * Map an Item to an output String representing formatted output for file
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @param <I> Input Item Type
 * @param <O> Output Line String Type
 * 
 * @see Mapper
 */
public interface ItemAggregator<I> extends Mapper<I, String> {

}
