/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.utilities;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import com.java.concurrent.utils.streams.files.writers.behaviors.FieldAssociation;

/**
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class FileUtilities {

	/**
	 * Constructor
	 */
	private FileUtilities() {
		throw new IllegalStateException("FileUtilities: Utility Class");
	}

	private static final String[] splitFieldBySeparator(String line, String separator) {
		String[] array = new String[0];
		if (line==null || separator==null ||
			line.trim().isEmpty() || separator.trim().isEmpty()) {
			return array;
		}
		return line.split(separator);
	}

	public static final String[] splitFieldByFixedLength(String line, FieldAssociation association) {
		String[] array = new String[0];
		if (line==null || association==null || association.getAssociations()==null ||
			line.trim().isEmpty() || association.getAssociations().isEmpty()) {
				return array;
		}
		AtomicInteger index=new AtomicInteger(0);
		return association.getAssociations()
		.stream( )
		.sorted( )
		.map( a -> {
			int fromIncluded = index.get();
			int toExcluded = index.get() + a.getFileFieldLength();
			index.set(toExcluded);
			return line.substring(fromIncluded, toExcluded);
		})
		.toArray( String[]::new );
	}
	
	public static final <T> T mapObject(String line, String separator, Supplier<T> generator, List<FieldExecutor<T>> executors) {
		T item = generator.get();
		String[] fields = splitFieldBySeparator(line, separator);
		AtomicInteger index=new AtomicInteger(0);
		executors
		.stream( )
		.sorted( )
		.forEach( e -> {
			try {
				e.exec(item, e.getClass().cast(fields[index.getAndIncrement()]));
			} catch (Exception ex) {
				throw new IllegalStateException(ex);
			}
		} );
		return item;
	}

	public static final <T> T mapObject(String line, FieldAssociation association, Supplier<T> generator, List<FieldExecutor<T>> executors) {
		T item = generator.get();
		String[] fields = splitFieldByFixedLength(line, association);
		AtomicInteger index=new AtomicInteger(0);
		executors
		.stream( )
		.sorted( )
		.forEach( e -> {
			try {
				e.exec(item, e.getClass().cast(fields[index.getAndIncrement()]));
			} catch (Exception ex) {
				throw new IllegalStateException(ex);
			}
		} );
		return item;
	}
}
