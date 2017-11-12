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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.concurrent.utils.streams.common.StreamReader;
import com.java.concurrent.utils.streams.common.exceptions.MaxNumberOfErrorsReachedException;
import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;
import com.java.concurrent.utils.streams.files.writers.behaviors.LineMapper;

/**
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class ItemReader<T> implements StreamReader<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemReader.class);
	
	private BufferedReader reader = null;
	
	private File file;

	private LineMapper<String, T> mapper;

	private AtomicLong discarded = new AtomicLong(0L);
	
	/**
	 * Constructor
	 * @param mapper
	 * @param file
	 * @throws StreamIOException
	 */
	public ItemReader(LineMapper<String, T> mapper, File file) throws StreamIOException {
		super();
		try {
			this.file = file;
			reader = new BufferedReader(new FileReader(file));
			this.open();
		} catch (Exception e) {
			throw new StreamIOException(e);
		}
		this.mapper = mapper;
	}

	/**
	 * Constructor
	 * @param mapper
	 * @param filePath
	 * @throws StreamIOException
	 */
	public ItemReader(LineMapper<String, T> mapper, Path filePath) throws StreamIOException {
		super();
		try {
			file = filePath.toFile();
			reader = new BufferedReader(new FileReader(file));
			this.open();
		} catch (Exception e) {
			throw new StreamIOException(e);
		}
		this.mapper = mapper;
	}

	/**
	 * Constructor
	 * @param mapper
	 * @param filePath
	 * @throws FileNotFoundException
	 */
	public ItemReader(LineMapper<String, T> mapper, String filePath) throws FileNotFoundException {
		super();
		file = new File(filePath);
		reader = new BufferedReader(new FileReader(file));
		this.mapper = mapper;
	}

	/**
	 * @return the mapper
	 */
	public LineMapper<String, T> getMapper() {
		return mapper;
	}

	/**
	 * @param mapper the mapper to set
	 */
	public void setMapper(LineMapper<String, T> mapper) {
		this.mapper = mapper;
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.StreamReader#read()
	 */
	@Override
	public T read() throws StreamIOException {
		synchronized (this) {
			try {
					return mapper.map(reader.readLine());
			} catch (Exception e) {
				this.notifyAll();
				LOGGER.error("Errors reading item", e);
				discarded.incrementAndGet();
				throw new StreamIOException("Errors reading item", e);
			} finally {
				this.notifyAll();
				if ( this.mapper.getMaxNumberOfReadErrors() > 0 && 
					 discarded.get() >= this.mapper.getMaxNumberOfReadErrors()  ) {
					throw new MaxNumberOfErrorsReachedException(this.mapper.getMaxNumberOfReadErrors(), discarded.get());
				}
			}
		}
	}
	
	/**
	 * Get Discarded counter value
	 * @return the discarded count value
	 */
	public long getDiscarded() {
		return discarded.get();
	}
	
	/**
	 * Reset Discarded counter
	 */
	public void resetDiscarded() {
		discarded.set(0L);
	}

	/* (non-Javadoc)
	 * @see com.java.concurrent.utils.streams.common.StreamReader#readAll()
	 */
	@Override
	public List<T> readAll() throws StreamIOException {
		synchronized (this) {
			try {
				return IOUtils.readLines(reader)
								.stream()
								.map(
									l -> {
										try {
											return mapper.map(l);
										} catch (Exception e) {
											LOGGER.error("Errors reading item from stream", e);
											discarded.incrementAndGet();
											if ( mapper.getMaxNumberOfReadErrors() > 0 && 
													 discarded.incrementAndGet() >= this.mapper.getMaxNumberOfReadErrors()  ) {
													throw new MaxNumberOfErrorsReachedException(this.mapper.getMaxNumberOfReadErrors(), discarded.get());
											}
											return null;
										}
								})
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			} catch (Exception e) {
				LOGGER.error("Errors reading items", e);
				throw new StreamIOException("Errors reading items", e);
			} finally {
				this.notifyAll();
				if ( this.mapper.getMaxNumberOfReadErrors() > 0 && 
						 discarded.incrementAndGet() >= this.mapper.getMaxNumberOfReadErrors()  ) {
						throw new StreamIOException("Reached Max Number Of Error");
				}
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
