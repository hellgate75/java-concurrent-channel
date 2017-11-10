/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.utilities;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.java.concurrent.utils.streams.files.writers.behaviors.FieldAssociation;

/**
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class ReflectionUtilities {

	/**
	 * Constructor
	 */
	private ReflectionUtilities() {
		throw new IllegalStateException("ReflectionUtilities: Utility Class");
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassFromSupplier(Supplier<T> supplier) {
		return (Class<T>)supplier.get().getClass();
	}


	private static final <T> FieldExecutor<T> buildExecutor(String fieldName, Class<T> clazz, AtomicInteger counter) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return new FieldExecutor<>(field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<FieldExecutor<T>> getFieldExecutors(Class<T> clazz, FieldAssociation fieldsFolder) {
		AtomicInteger index = new AtomicInteger(0);
		return fieldsFolder.getAssociations()
		.stream( )
		.sorted()
		.map( m -> { return buildExecutor(m.getItemFieldName(), clazz, index); } )
		.collect(Collectors.toList());
	}

}
