/**
 * 
 */
package com.java.concurrent.streams.channel;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.java.concurrent.streams.channel.Channel;
import com.java.concurrent.streams.channel.ChannelsRegistry;
import com.java.concurrent.streams.channel.exceptions.ChannelInstanceException;
import com.java.concurrent.streams.channel.exceptions.DuplicatedObjectException;

/**
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ChannelTest {
	
	@Test
	public void testStandAloneChannel() {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.type);
	}

	
	@Test
	public void testRegisteredChannel() throws ChannelInstanceException, DuplicatedObjectException {
		Channel<String> channel = Channel.createAndRegister("myChannel", String.class);
		assertEquals("Class Type Must match", String.class, channel.type);
		assertNotNull("Channel must be registered", ChannelsRegistry.get().getChannel("myChannel"));
		assertEquals("Channel in Registry must match", channel, ChannelsRegistry.get().getChannel("myChannel"));
	}

}
