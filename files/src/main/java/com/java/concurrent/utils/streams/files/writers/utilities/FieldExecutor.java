/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.utilities;

import java.lang.reflect.Field;

/**
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class FieldExecutor<T> {

	private Field field;

	/**
	 * Constructor
	 * @param field
	 */
	public FieldExecutor(Field field) {
		super();
		if (field==null)
			throw new IllegalStateException("Field cannot be null");
		this.field = field;
	}

	/**
	 * @return the field
	 */
	public Field getField() {
		return field;
	}
	
	public Class<?> getFieldValueClass() throws ClassNotFoundException {
		return Class.forName(field.getType().getName());
	}
	
	public void exec(T t, Object value) throws IllegalAccessException {
		field.setAccessible(true);
		field.set(t, value);
	}
	
	public Object exec(T t) throws IllegalAccessException {
		field.setAccessible(true);
		return field.get(t);
	}
	
}
