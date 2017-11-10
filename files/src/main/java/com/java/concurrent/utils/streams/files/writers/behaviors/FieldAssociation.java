package com.java.concurrent.utils.streams.files.writers.behaviors;

import java.util.List;

import com.java.concurrent.utils.streams.files.writers.models.FieldMetadata;

/**
 * Provide metadata association between file and Item field
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public interface FieldAssociation {

	/**
	 * Provides List of assciations between file line chunk and entity field
	 * @return list of field associations
	 */
	List<FieldMetadata> getAssociations();
}
