/**
 * 
 */
package com.java.concurrent.utils.streams.files.writers.models;

/**
 * Represent Field Association Map
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class FieldAssociation {

	private String itemFieldName=null;
	
	private String fileHeaderName=null;
	
	private int fileFieldPosition=-1;
	
	private int fileFieldLength=-1;

	/**
	 * Constructor for Delimited Files
	 * @param itemFieldName Name of Item field
	 * @param fileHeaderName name of field file header
	 */
	public FieldAssociation(String itemFieldName, String fileHeaderName) {
		super();
		if (itemFieldName==null)
			throw new NullPointerException("Item field name cannot be null");
		if (fileHeaderName==null)
			throw new NullPointerException("File field header name cannot be null");
		this.itemFieldName = itemFieldName;
		this.fileHeaderName = fileHeaderName;
	}

	/**
	 * Constructor for Delimited Files
	 * @param itemFieldName Name of Item field
	 * @param fileFieldPosition Position in line tokens
	 */
	public FieldAssociation(String itemFieldName, int fileFieldPosition) {
		super();
		if (itemFieldName==null)
			throw new NullPointerException("Item field name cannot be null");
		this.itemFieldName = itemFieldName;
		this.fileFieldPosition = fileFieldPosition;
	}

	/**
	 * Constructor for Fixed Length Fields Files
	 * @param itemFieldName Name of Item field
	 * @param fileFieldPosition Position in line chunks
	 * @param fileFieldLength Length of field text content
	 */
	public FieldAssociation(String itemFieldName, int fileFieldPosition, int fileFieldLength) {
		super();
		if (itemFieldName==null)
			throw new NullPointerException("Item field name cannot be null");
		this.itemFieldName = itemFieldName;
		this.fileFieldPosition = fileFieldPosition;
		this.fileFieldLength = fileFieldLength;
	}

	/**
	 * Get Name of Item field
	 * @return name of item field
	 */
	public String getItemFieldName() {
		return itemFieldName;
	}

	/**
	 * Get Name of field file header
	 * @return name of field file header
	 */
	public String getFileHeaderName() {
		return fileHeaderName;
	}

	/**
	 * Get Position in line tokens/chunks
	 * @return position in line tokens/chunks
	 */
	public int getFileFieldPosition() {
		return fileFieldPosition;
	}

	/**
	 * Get Length of field text content
	 * @return length of field text content
	 */
	public int getFileFieldLength() {
		return fileFieldLength;
	}

	
}
