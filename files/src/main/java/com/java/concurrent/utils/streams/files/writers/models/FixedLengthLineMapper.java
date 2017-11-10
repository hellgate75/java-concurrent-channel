/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.models;

import java.util.List;
import java.util.function.Supplier;

import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;
import com.java.concurrent.utils.streams.files.writers.behaviors.FieldAssociation;
import com.java.concurrent.utils.streams.files.writers.behaviors.HeaderAssociator;
import com.java.concurrent.utils.streams.files.writers.behaviors.LineMapper;
import com.java.concurrent.utils.streams.files.writers.utilities.FieldExecutor;
import com.java.concurrent.utils.streams.files.writers.utilities.FileUtilities;
import com.java.concurrent.utils.streams.files.writers.utilities.ReflectionUtilities;

/**
 * Provides feature for Mapping Delimited file lines to provided items
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * @param <I> Line String Type
 * @param <O> Item Entity Type
 * @see LineMapper
 */
public class FixedLengthLineMapper<I extends String, O> implements LineMapper<I, O> {

	private List<FieldExecutor<O>> executors;
	private FieldAssociation association;
	
	private HeaderAssociator headers;
	
	private String delimiter=",";
	
	private Supplier<O> generator;
	
	private Class<O> clazz;
	
	private long maxNumberOfReadErrors=-1L;
	
	/**
	 * Constructor
	 * @param generator Generate new Output Item Instance
	 * @param association Association for Item Fields
	 * @param delimter Field Line Delimiter
	 * @throws StreamIOException Thrown when Mapper initializer fails
	 */
	public FixedLengthLineMapper(Supplier<O> generator, FieldAssociation fieldsFolder, String delimiter) throws StreamIOException {
		this(generator, fieldsFolder);
		this.delimiter = delimiter;
	}

	/**
	 * Constructor that use as separator comma character
	 * @param generator Generate new Output Item Instance
	 * @param association Association for Item Fields
	 * @throws StreamIOException Thrown when Mapper initializer fails
	 */
	public FixedLengthLineMapper(Supplier<O> generator, FieldAssociation fieldsFolder) throws StreamIOException {
		super();
		this.association = fieldsFolder;
		this.generator=generator;
		try {
			clazz = ReflectionUtilities.getClassFromSupplier(generator);
			executors = ReflectionUtilities.getFieldExecutors(clazz, fieldsFolder);
		} catch (Exception e) {
			throw new StreamIOException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.files.writers.behaviors.LineMapper#getMaxNumberOfReadErrors()
	 */
	@Override
	public long getMaxNumberOfReadErrors() {
		return maxNumberOfReadErrors;
	}

	/**
	 * @param maxNumberOfReadErrors the maxNumberOfReadErrors to set
	 */
	public void setMaxNumberOfReadErrors(long maxNumberOfReadErrors) {
		this.maxNumberOfReadErrors = maxNumberOfReadErrors;
	}

	/**
	 * @return the headers
	 */
	public HeaderAssociator getHeaders() {
		return headers;
	}

	/**
	 * @return the generator
	 */
	public Supplier<O> getGenerator() {
		return generator;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(HeaderAssociator headers) {
		this.headers = headers;
	}

	/**
	 * @return the association
	 */
	public FieldAssociation getAssociation() {
		return association;
	}

	/**
	 * @return the delimter
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.behaviors.Mapper#map(java.lang.Object)
	 */
	@Override
	public O map(I i) throws StreamIOException {
		try {
			return FileUtilities.mapObject(i, delimiter, generator, executors);
		} catch (Exception e) {
			throw new StreamIOException(e);
		}
	}

}
