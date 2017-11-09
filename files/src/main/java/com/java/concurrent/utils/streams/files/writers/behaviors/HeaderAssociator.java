/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.behaviors;

import java.util.List;

/**
 * Provide file header names for written file
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public interface HeaderAssociator {
	/**
	 * Provide Header names to save file
	 * @return header names list
	 */
	List<String> getHeader();
}
