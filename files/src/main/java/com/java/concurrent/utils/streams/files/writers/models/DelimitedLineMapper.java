/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.models;

import com.java.concurrent.utils.streams.files.writers.behaviors.HeaderAssociator;
import com.java.concurrent.utils.streams.files.writers.behaviors.ItemMapper;

/**
 * Provides feature for Mapping Delimited file lines to provided items
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * @param <I> Line String Type
 * @param <O> Item Entity Type
 * @see ItemMapper
 */
@SuppressWarnings("hiding")
public class DelimitedLineMapper<I extends String, O> implements ItemMapper<I, O> {

	private FieldAssociation association;
	
	private HeaderAssociator headers;
	
	private String delimter=",";
	
	/**
	 * Constructor
	 * @param association Association for Item Fields
	 * @param delimter Field Line Delimiter
	 */
	public DelimitedLineMapper(FieldAssociation association, String delimter) {
		super();
		this.association = association;
		this.delimter = delimter;
	}

	/**
	 * Constructor
	 * @param association Association for Item Fields
	 */
	public DelimitedLineMapper(FieldAssociation association) {
		super();
		this.association = association;
	}

	/**
	 * @return the headers
	 */
	public HeaderAssociator getHeaders() {
		return headers;
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
	public String getDelimter() {
		return delimter;
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.behaviors.Mapper#map(java.lang.Object)
	 */
	@Override
	public O map(I i) {
		//TODO Implement mapper and transformer!!
		return null;
	}

}
