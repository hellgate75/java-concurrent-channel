/**
 * 
 */
package com.java.concurrent.utils.streams.channel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.MethodSorters;

import com.java.concurrent.utils.streams.channel.Channel;
import com.java.concurrent.utils.streams.channel.ChannelsRegistry;
import com.java.concurrent.utils.streams.common.exceptions.StreamInstanceException;
import com.java.concurrent.utils.streams.common.exceptions.DuplicatedObjectException;

/**
 * Testing Channel Registry Helper
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @see ChannelsRegistry
 */
@RunWith(BlockJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterChannelUnitTest {

	
	@Test()
	public void testRegistryCannelRegister() throws StreamInstanceException, DuplicatedObjectException {
		ChannelsRegistry.get().unregisterChannel("fakeName");
		Channel<String> channel = new Channel<>(String.class);
		ChannelsRegistry.get().registerChannel(channel, "fakeName");
		assertEquals("Registered Channel must match with opted-in one", channel, ChannelsRegistry.get().getChannel("fakeName"));
	}

	@Test(expected=StreamInstanceException.class)
	public void testRegistryCannelRegisterChannelInstanceExceptionCauseByChannel() throws StreamInstanceException, DuplicatedObjectException {
		ChannelsRegistry.get().unregisterChannel("fakeName");
		ChannelsRegistry.get().registerChannel(null, "fakeName");
		assertNull("Registered Channel must be NULL",ChannelsRegistry.get().getChannel("fakeName"));
	}

	@Test(expected=StreamInstanceException.class)
	public void testRegistryCannelRegisterChannelInstanceExceptionCauseByName() throws StreamInstanceException, DuplicatedObjectException {
		ChannelsRegistry.get().unregisterChannel("fakeName");
		Channel<String> channel = new Channel<>(String.class);
		ChannelsRegistry.get().registerChannel(channel, null);
		assertNull("Registered Channel must be NULL",ChannelsRegistry.get().getChannel("fakeName"));
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testRegistryCannelRegisterDuplicatedObjectException() throws StreamInstanceException, DuplicatedObjectException {
		ChannelsRegistry.get().unregisterChannel("fakeName");
		Channel<String> channel = new Channel<>(String.class);
		ChannelsRegistry.get().registerChannel(channel, "fakeName");
		ChannelsRegistry.get().registerChannel(channel, "fakeName");
		assertEquals("Registered Channel must match with opted-in one", channel, ChannelsRegistry.get().getChannel("fakeName"));
	}

	@Test()
	public void testRegistryChannelUnRegisterExistingChannel() throws StreamInstanceException, DuplicatedObjectException {
		ChannelsRegistry.get().unregisterChannel("fakeName");
		Channel<String> channel = new Channel<>(String.class);
		ChannelsRegistry.get().registerChannel(channel, "fakeName");
		assertEquals("Registered Channel must match with opted-in one", channel, ChannelsRegistry.get().getChannel("fakeName"));
		assertTrue("Channel must be removed correctly", ChannelsRegistry.get().unregisterChannel("fakeName"));
	}

	@Test()
	public void testRegistryChannelUnRegisterNotExistingChannel() throws StreamInstanceException, DuplicatedObjectException {
		ChannelsRegistry.get().unregisterChannel("fakeName");
		assertNull("No Channel was opted-in one", ChannelsRegistry.get().getChannel("fakeName"));
		assertFalse("Channel must be removed correctly", ChannelsRegistry.get().unregisterChannel("fakeName"));
	}
}
