/**
 * 
 */
package com.java.concurrent.utils.streams.files.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.concurrent.utils.streams.common.StreamReader;
import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;

/**
 * Flat File Reader, Blocking Concurrent reader
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @see StreamReader
 */
public class FlatFileReader implements StreamReader<String> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileReader.class);
	
	private BufferedReader reader = null;
	
	private File file;

	/**
	 * Constructor
	 * @throws FileNotFoundException 
	 */
	public FlatFileReader(Path filePath) throws StreamIOException {
		super();
		try {
			file = filePath.toFile();
			reader = new BufferedReader(new FileReader(file));
			this.open();
		} catch (Exception e) {
			throw new StreamIOException(e);
		}
	}

	/**
	 * Constructor
	 * @throws FileNotFoundException 
	 */
	public FlatFileReader(File file) throws StreamIOException {
		super();
		try {
			this.file = file;
			reader = new BufferedReader(new FileReader(file));
			this.open();
		} catch (Exception e) {
			throw new StreamIOException(e);
		}
	}

	/**
	 * Constructor
	 * @throws FileNotFoundException 
	 */
	public FlatFileReader(String filePath) throws FileNotFoundException {
		super();
		file = new File(filePath);
		reader = new BufferedReader(new FileReader(file));
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.StreamReader#read()
	 */
	@Override
	public String read() throws StreamIOException {
		synchronized (this) {
			try {
					return reader.readLine();
			} catch (Exception e) {
				this.notifyAll();
				LOGGER.error("Errors reading file", e);
				throw new StreamIOException("Errors reading file", e);
			} finally {
				this.notifyAll();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.StreamReader#readAll()
	 */
	@Override
	public List<String> readAll() throws StreamIOException {
		synchronized (this) {
			try {
					return IOUtils.readLines(reader);
			} catch (Exception e) {
				LOGGER.error("Errors reading file", e);
				throw new StreamIOException("Errors reading file", e);
			} finally {
				this.notifyAll();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.StreamReader#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		try {
			return reader.ready();
		} catch (Exception e) {
			LOGGER.warn("Errors recoverying informtion from file", e);
			return false;
		}
	}

	private void open() throws StreamIOException {
			if (reader==null) {
				try {
					reader = new BufferedReader(new FileReader(file));
				} catch (Exception e) {
					LOGGER.error("Errors opening file", e);
					throw new StreamIOException("Errors opening file", e);
				}
			} else {
				LOGGER.warn("Stream already open!!");
			}
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.StreamReader#close()
	 */
	@Override
	public void close() throws StreamIOException {
		synchronized (this) {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {
				LOGGER.error("Errors closing file", e);
			} finally {
				this.notifyAll();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.StreamReader#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return reader!=null;
	}

	
}
