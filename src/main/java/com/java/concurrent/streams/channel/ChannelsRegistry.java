/**
 * 
 */
package com.java.concurrent.streams.channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.java.concurrent.streams.channel.exceptions.ChannelInstanceException;
import com.java.concurrent.streams.channel.exceptions.DuplicatedObjectException;

/**
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
public class ChannelsRegistry {
	
	private static final ChannelsRegistry instance = new ChannelsRegistry();

	private final ConcurrentMap<String, Channel<?>> registry = new ConcurrentHashMap<>();

	/**
	 * Constructor
	 */
	private ChannelsRegistry() {
		super();
	}
	
	/**
	 * Method used by queue to register Channels to registry
	 * @param channel Channel Instance
	 * @param channelName Registration name
	 * @throws ChannelInstanceException Thrown when Channel or Name are inappropriate for Registry
	 * @throws DuplicatedObjectException Thrown when try to register a Channel with an already used name
	 */
	protected <T> void registerChannel(Channel<T> channel, String channelName) throws ChannelInstanceException, DuplicatedObjectException {
		if (channel == null) {
			throw new ChannelInstanceException("Channel with name " + channelName + " cannot be null");
		}
		if (channelName == null || channelName.trim().isEmpty()) {
			throw new ChannelInstanceException("Channel name cannot be null or empty");
		}
		if (registry.putIfAbsent(channelName, channel) != null ) {
			throw new DuplicatedObjectException("Channel with name " + channelName + " already existing");
		}
	}

	/**
	 * Method used by queue to unregister Channels from registry
	 * @param channelName Channel name to unregister from registry
	 * @return remove status flag
	 */
	protected boolean unregisterChannel(String channelName){
		return registry.remove(channelName)!=null;
	}
	
	/**
	 * Retrieve Channel of Given name from registry, or return null
	 * @param channelName name of channel in registry
	 * @return Registry Channel corresponding to stored Channel name
	 */
	@SuppressWarnings("unchecked")
	public <T> Channel<T> getChannel(String channelName) {
		return (Channel<T>) registry.get(channelName);
	}

	/**
	 * Retrieve the Singleton instanco of registry
	 * @return the Registry
	 */
	public static final ChannelsRegistry get() {
		return instance;
	}
	
}
