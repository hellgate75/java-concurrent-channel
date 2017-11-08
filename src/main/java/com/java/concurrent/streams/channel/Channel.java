/**
 * 
 */
package com.java.concurrent.streams.channel;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.concurrent.streams.channel.exceptions.ChannelIOException;
import com.java.concurrent.streams.channel.exceptions.ChannelInstanceException;
import com.java.concurrent.streams.channel.exceptions.ChannelNullableAssignementException;
import com.java.concurrent.streams.channel.exceptions.DuplicatedObjectException;
import com.java.concurrent.streams.channel.listeners.ChannelInputListener;
import com.java.concurrent.streams.channel.listeners.ChannelOutputListener;
import com.java.concurrent.streams.channel.operators.ChannelConsumer;
import com.java.concurrent.streams.channel.operators.ChannelFilter;
import com.java.concurrent.streams.channel.operators.ChannelProducer;

/**
 * Parametric Concurrent Non-Blocking Channel stream. It implements Producer behavior, to provide abstraction level Producer capabilities.<br><br>
 * This component has been designed to supply java missing of counter-part Go Language channel type, within more Java Object Oriented capabilities.<br><br>
 * This demo version count offered and consumed elements with scale of {@link Long} scale. It will early released a more efficient and capable version in a production branch.<br><br>
 * This version is provided of a Consumer Thread that can be manually activated, because it requires presence of at least one {@link ChannelConsumer} registered in the Channel output operations.
 * 
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 * @param <T> Type for channel
 * 
 * @see ChannelsRegistry
 * @see ChannelConsumer
 * @see ChannelProducer
 * @see ChannelFilter
 * @see ChannelOutputListener
 * @see ChannelInputListener
 */
public class Channel<T> implements ChannelProducer<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Channel.class);
	
	private ChannelThread<T> thread = new ChannelThread<>(this);

	private String channelName;

	private final ConcurrentLinkedQueue<T> channelQueue = new ConcurrentLinkedQueue<>();
	
	private final ConcurrentLinkedQueue<ChannelInputListener<T>> inputListeners = new ConcurrentLinkedQueue<>();

	private final ConcurrentLinkedQueue<ChannelOutputListener<T>> outputListeners = new ConcurrentLinkedQueue<>();

	private final ConcurrentLinkedQueue<ChannelConsumer<T>> consumers = new ConcurrentLinkedQueue<>();

	private final ConcurrentLinkedQueue<ChannelFilter<T>> filters = new ConcurrentLinkedQueue<>();
	
	protected Class<T> type;
	
	protected AtomicLong totalElements = new AtomicLong(0L);
	
	protected AtomicLong totalConsumedElements = new AtomicLong(0L);
	
	/**
	 * Constructor for stand-alone Channels
	 * @param type Channel Objects type
	 */
	public Channel(Class<T> type) {
		super();
		this.type=type;
		this.channelName = null;
	}

	
	/**
	 * Constructor used to create and Store Channel in the {@link ChannelsRegistry}
	 * @param name Registry entry name
	 * @param type Channel Objects type
	 * @throws ChannelInstanceException Thrown when Name is inappropriate for Registry
	 * @throws DuplicatedObjectException Thrown when try to register a Channel with an already used name
	 */
	private Channel(String name, Class<T> type) throws ChannelInstanceException, DuplicatedObjectException {
		this(type);
		channelName = name;
		ChannelsRegistry.get().registerChannel(this, name);
	}
	
	/**
	 * Add a filter to the channel input operations
	 * @param filter Filter to add the queue input operations
	 * @throws ChannelNullableAssignementException Thrown when provided filter is null
	 * @throws DuplicatedObjectException Throw when the  filter is registered twice
	 * @see ChannelFilter
	 */
	public void addChannelFilter(ChannelFilter<T> filter) throws ChannelNullableAssignementException, DuplicatedObjectException {
		if (filter==null) {
			throw new ChannelNullableAssignementException("Forbidden registration of nullable filters");
		}
		if (filters.contains(filter)) {
			throw new DuplicatedObjectException("This listener has been already registered");
		}
		filters.add(filter);
	}
	
	/**
	 * Remove a filter from the channel input operations
	 * @param filter Filter to remove from the queue input operations
	 * @throws ChannelNullableAssignementException Thrown when provided filter is null
	 * @see ChannelFilter
	 */
	public void removeChannelFilter(ChannelFilter<T> filter) throws ChannelNullableAssignementException {
		if (filter==null) {
			throw new ChannelNullableAssignementException("Forbidden unregistration of nullable filters");
		}
		filters.remove(filter);
	}
	
	/**
	 * Add a consumer to the channel output operations
	 * @param consumer Consumer to add the queue output operations
	 * @throws ChannelNullableAssignementException Thrown when provided consumer is null
	 * @throws DuplicatedObjectException Throw when the  lister is registered twice
	 * @see ChannelConsumer
	 */
	public void addChannelConsumer(ChannelConsumer<T> consumer) throws ChannelNullableAssignementException, DuplicatedObjectException {
		if (consumer==null) {
			throw new ChannelNullableAssignementException("Forbidden registration of nullable consumers");
		}
		if (consumers.contains(consumer)) {
			throw new DuplicatedObjectException("This consumer has been already registered");
		}
		consumers.add(consumer);
	}
	
	/**
	 * Remove a consumer from the channel output operations
	 * @param consumer Consumer to be registered to output operations
	 * @throws ChannelNullableAssignementException Thrown when provided consumer is null
	 * @see ChannelConsumer
	 */
	public void removeChannelConsumer(ChannelConsumer<T> consumer) throws ChannelNullableAssignementException {
		if (consumer==null) {
			throw new ChannelNullableAssignementException("Forbidden unregistration of nullable consumers");
		}
		consumers.remove(consumer);
	}

	
	/**
	 * Add a channel input listener to the channel input operations
	 * @param listener channel input listener to add to the channel input  operations
	 * @throws ChannelNullableAssignementException Thrown when provided listener is null
	 * @throws DuplicatedObjectException Throw when the  lister is registered twice
	 * @see ChannelInputListener
	 */
	public void addChannelInputListener(ChannelInputListener<T> listener) throws ChannelNullableAssignementException, DuplicatedObjectException {
		if (listener==null) {
			throw new ChannelNullableAssignementException("Forbidden registration of nullable input listener");
		}
		if (inputListeners.contains(listener)) {
			throw new DuplicatedObjectException("This input listener has been already registered");
		}
		inputListeners.add(listener);
	}
	
	/**
	 * Remove a channel input listener from the channel input operations
	 * @param listener Channel input listener to be registered to input operations
	 * @throws ChannelNullableAssignementException Thrown when provided listener is null
	 * @see ChannelInputListener
	 */
	public void removeChannelInputListener(ChannelInputListener<T> listener) throws ChannelNullableAssignementException {
		if (listener==null) {
			throw new ChannelNullableAssignementException("Forbidden unregistration of nullable input listener");
		}
		inputListeners.remove(listener);
	}
	
	/**
	 * Add a channel output listener to the channel output operations
	 * @param listener channel output listener to add to the channel output  operations
	 * @throws ChannelNullableAssignementException Thrown when provided listener is null
	 * @throws DuplicatedObjectException Throw when the  lister is registered twice
	 * @see ChannelOutputListener
	 */
	public void addChannelOutputListener(ChannelOutputListener<T> listener) throws ChannelNullableAssignementException, DuplicatedObjectException {
		if (listener==null) {
			throw new ChannelNullableAssignementException("Forbidden registration of nullable output listener");
		}
		if (outputListeners.contains(listener)) {
			throw new DuplicatedObjectException("This output listener has been already registered");
		}
		outputListeners.add(listener);
	}
	
	/**
	 * Remove a channel output listener from the channel output operations
	 * @param listener Channel output listener to be registered to output operations
	 * @throws ChannelNullableAssignementException Thrown when provided listener is null
	 * @see ChannelOutputListener
	 */
	public void removeChannelOutputListener(ChannelOutputListener<T> listener) throws ChannelNullableAssignementException {
		if (listener==null) {
			throw new ChannelNullableAssignementException("Forbidden unregistration of nullable output listener");
		}
		outputListeners.remove(listener);
	}

	/**
	 * Retrieve Channel name, if stored in registry or null in stand alone use
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}


	/**
	 * Retrieve class type, used for confirmation of managed type superclass.
	 * @return the type
	 */
	public Class<T> getType() {
		return type;
	}


	/* (non-Javadoc)
	 * @see com.streams.channel.operators.ChannelProducer#add(java.lang.Object)
	 */
	@Override
	public boolean add(T t) throws ChannelNullableAssignementException, ChannelIOException {
		if (t==null) {
			throw new ChannelNullableAssignementException("Forbidden registration of nullable element");
		}
		try {
			long results = this.filters.stream()
				.filter( (n) -> n.accept(t) )
				.collect(Collectors.counting());
			if (results == this.filters.size()) {
				this.channelQueue.add(t);
				this.inputListeners.parallelStream()
					.forEach( l -> l.accepted(t));
				totalElements.incrementAndGet();
				return true;
			} else {
				this.inputListeners.parallelStream()
				.forEach( l -> l.rejected(t));
				return false;
			}
		} catch (Exception e) {
			throw new ChannelIOException("Unable to add value", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.streams.channel.operators.ChannelProducer#add(java.lang.Object[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public long add(T ...t) throws ChannelNullableAssignementException, ChannelIOException {
		if (t==null) {
			throw new ChannelNullableAssignementException("Forbidden registration of nullable element list");
		}
		AtomicLong counter = new AtomicLong(0L);
		try {
			Arrays.asList(t).parallelStream().forEach( v -> addToQueue(v, counter) );
		} catch (Exception e) {
			throw new ChannelIOException("Unable to add values", e);
		}
		return counter.get();
	}

	/* (non-Javadoc)
	 * @see com.streams.channel.operators.ChannelProducer#add(java.util.Collection)
	 */
	@Override
	public long add(Collection<T> collection) throws ChannelNullableAssignementException, ChannelIOException {
		if (collection==null) {
			throw new ChannelNullableAssignementException("Forbidden registration of nullable collection");
		}
		AtomicLong counter = new AtomicLong(0L);
		try {
			collection.parallelStream().forEach( v -> addToQueue(v, counter) );
		} catch (Exception e) {
			throw new ChannelIOException("Unable to add values", e);
		}
		return counter.get();
	}
	
	private void addToQueue(T v, AtomicLong counter) {
		try {
			if ( add(v) ) {
				counter.incrementAndGet();
			}
		} catch (Exception e) {
			LOGGER.error("Error adding element : " + v);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieve number of elements Added in the queue, since last Channel clear
	 * @return the Total Added Elements
	 */
	public long getTotalElements() {
		return totalElements.get();
	}


	/**
	 * Retrieve number of elements Consumed from the queue, since last Channel start
	 * @return the Total Consumed Elements
	 */
	public long getTotalConsumedElements() {
		return totalConsumedElements.get();
	}


	/**
	 * Poll an element from queue
	 * @return
	 * @throws ChannelIOException
	 */
	public T poll() throws ChannelIOException {
		T t = null;
		if ( ( ( t = this.channelQueue.poll() ) != null) ) {
			totalConsumedElements.incrementAndGet();
			return t;
		}
		throw new ChannelIOException("Channel is Empty");
	}

	/**
	 * Retrieve Channel is empty
	 * @return Empty state
	 */
	public boolean isEmpty() {
		return this.channelQueue.isEmpty();
	}

	/**
	 * Clear Channel
	 */
	public void clear() {
		this.totalElements.set(0L);
		this.channelQueue.clear();
	}

	/**
	 * Retains only the elements in this collection that are contained in the specified collection (optional operation). In other words, removes from this collection all of its elements that are not contained in the specified collection.<br><br>
	 *
	 * This implementation iterates over this collection, checking each element returned by the iterator in turn to see if it's contained in the specified collection. If it's not so contained, it's removed from this collection with the iterator's remove method.<br><br>
	 *
	 * Note that this implementation will throw an UnsupportedOperationException if the iterator returned by the iterator method does not implement the remove method and this collection contains one or more elements not present in the specified collection.<br><br>
	 * 
	 * ATTENTION: CURRENT COUNTER VALUE WILL BE RESETTED SYNCHRONOUSLY DURING THIS OPERATION, AND IT MEANS THAT TO PREVENT COUNTER VALUE MISMATCHING DURING THIS OPERATION NO VALUE MUST BE INSERTED INTO THE CHANNEL UP TO THE END OF RETAIN EXECUTION!!
	 * @param collection Collection of Element to retain from Channel
	 * @see ConcurrentLinkedQueue#retainAll(Collection)
	 */
	public void retainAll(Collection<T> collection) {
		this.channelQueue.retainAll(collection);
		totalElements.set(this.channelQueue.size());
	}


	/**
	 * Start Channel Consume Thread
	 * @throws ChannelIOException Throw if there is no Consumer in Channel output operations
	 */
	public void start() throws ChannelIOException {
		this.totalConsumedElements.set(0L);
		thread.startConsuming();
	}
	
	/**
	 * Retrieve if Channel Consume Thread is Running
	 * @return Channel Consume Thread is Running
	 */
	public boolean running() {
		return thread.isRunning();
	}
	
	/**
	 * Stop Channel Consume Thread if running
	 */
	public void stop() {
		thread.stopConsuming();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			if (channelName!=null)
				ChannelsRegistry.get().unregisterChannel(channelName);
		} catch (Exception e) {
			LOGGER.error("Error unregitering Channel" + channelName, e);
		}
		
		if (thread!=null && thread.isRunning()) {
			thread.stopConsuming();
		}
		super.finalize();
	}


	/**
	 * Create a channel and Store it in the {@link ChannelsRegistry}
	 * @param channelName Registry entry name
	 * @param type Channel Registration type
	 * @throws ChannelInstanceException Thrown when Name is inappropriate for Registry
	 * @throws DuplicatedObjectException Thrown when try to register a Channel with an already used name
	 */
	public static final <T> Channel<T> createAndRegister(String channelName, Class<T> type) throws ChannelInstanceException, DuplicatedObjectException {
		return new Channel<>(channelName, type);
	}
	
	private static class ChannelThread<T> extends Thread {
		private Channel<T> channelInstance;
		
		private boolean running = false;
		
		/**
		 * Constructor
		 * @param channel running channel
		 */
		private ChannelThread(Channel<T> channel) {
			super();
			this.channelInstance = channel;
		}

		public synchronized void startConsuming() throws ChannelIOException {
			if (channelInstance.consumers.isEmpty()) {
				throw new ChannelIOException("No Consumer Defined");
			}
			if (! this.running) {
				this.running = true;
				super.start();
			}
		}

		public boolean isRunning() {
			return running;
		}

		public synchronized void stopConsuming() {
			if (! channelInstance.consumers.isEmpty() && running) {
				this.running = false;
				while (!channelInstance.channelQueue.isEmpty()) {
					this.running = false;
				}
			}
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (running || !channelInstance.channelQueue.isEmpty()) {
				if (channelInstance.channelQueue.isEmpty()) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				channelInstance.channelQueue.parallelStream()
				.forEach( n -> {
					channelInstance.consumers.parallelStream()
						.forEach( c -> c.consume(n) );
					channelInstance.outputListeners.parallelStream()
						.forEach( l -> l.consumed(n) );
					channelInstance.channelQueue.remove(n);
					channelInstance.totalConsumedElements.incrementAndGet();
				});
			}
		}
		
	}
}
