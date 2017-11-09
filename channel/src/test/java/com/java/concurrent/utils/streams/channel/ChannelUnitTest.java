/**
 * 
 */
package com.java.concurrent.utils.streams.channel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.concurrent.utils.streams.channel.listeners.ChannelInputListener;
import com.java.concurrent.utils.streams.channel.listeners.ChannelOutputListener;
import com.java.concurrent.utils.streams.common.StreamWriter;
import com.java.concurrent.utils.streams.common.behaviors.StreamConsumer;
import com.java.concurrent.utils.streams.common.behaviors.StreamFilter;
import com.java.concurrent.utils.streams.common.exceptions.DuplicatedObjectException;
import com.java.concurrent.utils.streams.common.exceptions.StreamIOException;
import com.java.concurrent.utils.streams.common.exceptions.StreamInstanceException;
import com.java.concurrent.utils.streams.common.exceptions.StreamNullableAssignementException;

/**
 * Testing Channel Element
 * @author Fabrizio Torelli &lt;hellgate75@gmail.com&gt;
 *
 * @see Channel
 */
@RunWith(BlockJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChannelUnitTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelUnitTest.class);
	
	@Test
	public void testStandAloneChannelInstance() throws StreamInstanceException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		ChannelsRegistry.get().registerChannel(channel, "MyStandAloneChannel");
		assertEquals("StandAlone manual opted-in Channel in Registry must match", channel, ChannelsRegistry.get().getChannel("MyStandAloneChannel"));
		assertNull("Class Name Must be null", channel.getChannelName());
		assertTrue("Channel is Empty", channel.isEmpty());
	}
	
	@Test(expected=StreamNullableAssignementException.class)
	public void testStandAloneChannelAddSingleNullableAssignementException() throws StreamNullableAssignementException, StreamIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String value=null;
		channel.write(value);
	}
	
	@Test(expected=StreamIOException.class)
	public void testStandAloneChannelAddSingleChannelIOException() throws StreamNullableAssignementException, StreamIOException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addFilter( t -> {
			if (t==null || t.trim().isEmpty())
				throw new NullPointerException("Value not nullable");
			return !t.contains("9");
		});
		String value="";
		channel.write(value);
		
	}
	
	@Test(expected=StreamNullableAssignementException.class)
	public void testStandAloneChannelAddListNullableAssignementException() throws StreamNullableAssignementException, StreamIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		List<String> valueList = null;
		channel.write(valueList);
		
	}
	
	@Test(expected=StreamIOException.class)
	public void testStandAloneChannelAddListChannelIOException() throws StreamNullableAssignementException, StreamIOException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		StreamFilter<String> filter = new StreamFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				if (t==null || t.trim().isEmpty())
					throw new NullPointerException("Value not nullable");
				return !t.contains("9");
			}
		};
		channel.addFilter(filter);
		String value=null;
		List<String> valueList = new ArrayList<String>(0);
		valueList.add(value);
		channel.write(valueList);
	}
	
	@Test(expected=StreamNullableAssignementException.class)
	public void testStandAloneChannelAddArrayNullableAssignementException() throws StreamNullableAssignementException, StreamIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String[] value=null;
		channel.write(value);
		
	}
	
	@Test(expected=StreamIOException.class)
	public void testStandAloneChannelAddArrayChannelIOException() throws StreamNullableAssignementException, StreamIOException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addFilter( t -> {
			if (t==null || t.trim().isEmpty())
				throw new NullPointerException("Value not nullable");
			return !t.contains("9");
		});
		String value=null;
		channel.write(new String[] {value});
	}
	
	@Test()
	public void testStandAloneChannelPoll() throws StreamIOException, DuplicatedObjectException, StreamNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String value="A";
		channel.write(new String[] {value});
		assertEquals("Polled value must be same than the added one","A", channel.read());
		assertTrue("Channel is Empty", channel.isEmpty());
	}
	
	@Test(expected=StreamIOException.class)
	public void testStandAloneChannelPollChannelIOException() throws StreamIOException, DuplicatedObjectException, StreamNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		assertTrue("Channel is Empty", channel.isEmpty());
		assertEquals("Polled value must be same than the added one","A", channel.read());
	}
	
	@Test()
	public void testStandAloneChannelRetainAll() throws StreamIOException, DuplicatedObjectException, StreamNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String value="A";
		channel.write(new String[] {value});
		List<String> retainList = new ArrayList<>(0);
		retainList.add("B");
		channel.retainAll(retainList);
		assertTrue("Channel is Empty", channel.isEmpty());
	}
	
	@Test
	public void testRegisteredChannelInstance() throws StreamInstanceException, DuplicatedObjectException {
		Channel<String> channel = Channel.createAndRegister("MyChannel", String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		assertEquals("Class Name Must match", "MyChannel", channel.getChannelName());
		assertEquals("Channel in Registry must match", channel, ChannelsRegistry.get().getChannel("MyChannel"));
		Channel<String> channelFromRegistry=ChannelsRegistry.get().getChannel("MyChannel");
		assertNotNull("Channel must be registered correctly", channelFromRegistry);
		assertEquals("Channel in Registry must match", channelFromRegistry, channel);
		ChannelsRegistry.get().unregisterChannel("MyChannel");
		assertNull("Channel must be unregistered", ChannelsRegistry.get().getChannel("MyChannel"));
		assertTrue("Channel is Empty", channel.isEmpty());
	}

	@Test(timeout=8000)
	public void testChannelProducer() {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.type);
		runThreadsOnProducerForArray(channel, 100, 1000);
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total produced elements must be same than requested ones on multi-threding", 
				channel.getTotalElements(), 100000);
		assertEquals("Total consumed elements must be zero", 
				channel.getTotalConsumedElements(), 0);
		assertFalse("Channel is not Empty", channel.isEmpty());
		channel.clear();
		assertTrue("Channel is Empty", channel.isEmpty());
	}

	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelProducerChannelNullableAssignementException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addConsumer(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelProducerChannelDuplicatedObjectException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		StreamConsumer<String> consumer = new StreamConsumer<String>() {
			
			@Override
			public void consume(String t) {
				LOGGER.warn("Consumed: " + t);
			}
		};
		channel.addConsumer(consumer);
		channel.addConsumer(consumer);
		
	}

	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelProducerRemoveChannelNullableAssignementException() throws StreamNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeConsumer(null);
		
	}

	@Test(timeout=15000)
	public void testChannelProducerAndConsume() throws StreamNullableAssignementException, DuplicatedObjectException, StreamIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		runThreadsOnProducer(channel, 100, 1000);
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total produced elements must be same than requested ones on multi-threding", 
				100000, channel.getTotalElements());
		assertEquals("Total consumed elements must be zero", 
				0, channel.getTotalConsumedElements());
		assertFalse("Channel is not Empty", channel.isEmpty());
		StreamConsumer<String> consumer = new StreamConsumer<String>() {
			
			@Override
			public void consume(String t) {
				LOGGER.warn("Consumed: " + t);
			}
		};
		channel.addConsumer(consumer);
		channel.open();
		assertTrue("Consumer Thread Must be running", channel.isOpen());
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		channel.close();
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total consumed elements must same than produced ones", 
				channel.getTotalElements(), channel.getTotalConsumedElements());
		channel.removeConsumer(consumer);
		assertTrue("Channel is Empty", channel.isEmpty());
	}

	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelInputListenerChannelNullableAssignementException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addChannelInputListener(null);
		
	}

	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelInputListenerRemoveChannelNullableAssignementException() throws StreamNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeChannelInputListener(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelInputListenerChannelDuplicatedObjectException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		AtomicLong readCount = new AtomicLong(0L);
		ChannelInputListener<String> listener = new ChannelInputListener<String>() {
			
			@Override
			public void rejected(String t) {
				// No Rejected In this test case
			}
			
			@Override
			public void accepted(String t) {
				readCount.incrementAndGet();
			}
		};
		channel.addChannelInputListener(listener);
		channel.addChannelInputListener(listener);
		
	}

	@Test(timeout=8000)
	public void testChannelInputListener() throws StreamNullableAssignementException, DuplicatedObjectException, StreamIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		AtomicLong readCount = new AtomicLong(0L);
		ChannelInputListener<String> listener = new ChannelInputListener<String>() {
			
			@Override
			public void rejected(String t) {
				// No Rejected In this test case
			}
			
			@Override
			public void accepted(String t) {
				readCount.incrementAndGet();
			}
		};
		channel.addChannelInputListener(listener);
		runThreadsOnProducer(channel, 100, 1000);
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total produced elements must be same than requested ones on multi-threding", 
				100000, channel.getTotalElements());
		assertEquals("Total consumed elements must be zero", 
				0, channel.getTotalConsumedElements());
		assertEquals("Total read must be same as produced elements on multi-threding", 
				100000, readCount.get());
		channel.removeChannelInputListener(listener);
		assertFalse("Channel is not Empty", channel.isEmpty());
	}

	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelOutputListenerChannelNullableAssignementException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addChannelOutputListener(null);
		
	}

	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelOutputListenerRemoveChannelNullableAssignementException() throws StreamNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeChannelOutputListener(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelOutputListenerChannelDuplicatedObjectException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		AtomicLong writeCount = new AtomicLong(0L);
		ChannelOutputListener<String> listener = new ChannelOutputListener<String>() {
			
			@Override
			public void consumed(String... t) {
				for( int i=0; i<t.length; i++)
					writeCount.incrementAndGet();
				
			}
			
			@Override
			public void consumed(String t) {
				writeCount.incrementAndGet();
			}
		};
		channel.addChannelOutputListener(listener);
		channel.addChannelOutputListener(listener);
	}

	@Test(timeout=15000)
	public void testChannelOutputListener() throws StreamNullableAssignementException, DuplicatedObjectException, StreamIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		AtomicLong writeCount = new AtomicLong(0L);
		ChannelOutputListener<String> listener = new ChannelOutputListener<String>() {
			
			@Override
			public void consumed(String... t) {
				for( int i=0; i<t.length; i++)
					writeCount.incrementAndGet();
				
			}
			
			@Override
			public void consumed(String t) {
				writeCount.incrementAndGet();
			}
		};
		channel.addChannelOutputListener(listener);
		runThreadsOnProducer(channel, 100, 1000);
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total produced elements must be same than requested ones on multi-threding", 
				100000, channel.getTotalElements());
		assertEquals("Total consumed elements must be zero", 
				0, channel.getTotalConsumedElements());
		channel.addConsumer(new StreamConsumer<String>() {
			
			@Override
			public void consume(String t) {
				// Only for compliance to writer process
				// This is not part of test
			}
		});
		channel.open();
		assertTrue("Consumer Thread Must be running", channel.isOpen());
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		channel.close();
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total consumed elements must same than produced ones", 
				channel.getTotalElements(), channel.getTotalConsumedElements());
		assertEquals("Total written elements must same than consumed ones", 
				channel.getTotalConsumedElements(), writeCount.get());
		channel.removeChannelOutputListener(listener);
	}
	
	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelFilterChannelNullableAssignementException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addFilter(null);
		
	}

	@Test(expected=StreamNullableAssignementException.class)
	public void testChannelFilterRemoveChannelNullableAssignementException() throws StreamNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeFilter(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelFilterChannelDuplicatedObjectException() throws StreamNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		StreamFilter<String> filter = new StreamFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				return !t.contains("9");
			}
		};
		channel.addFilter(filter);
		channel.addFilter(filter);
	}

	@Test(timeout=8000)
	public void testChannelFilter() throws StreamNullableAssignementException, DuplicatedObjectException, StreamIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		AtomicLong readCount = new AtomicLong(0L);
		AtomicLong rejectCount = new AtomicLong(0L);
		StreamFilter<String> filter = new StreamFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				return !t.contains("9");
			}
		};
		channel.addFilter(filter);
		ChannelInputListener<String> listener = new ChannelInputListener<String>() {
			
			@Override
			public void rejected(String t) {
				rejectCount.incrementAndGet();
			}
			
			@Override
			public void accepted(String t) {
				readCount.incrementAndGet();
			}
		};
		channel.addChannelInputListener(listener);
		runThreadsOnProducer(channel, 100, 1000);
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total produced elements must be same than requested ones on multi-threding", 
				81000, channel.getTotalElements());
		assertEquals("Total consumed elements must be zero", 
				0, channel.getTotalConsumedElements());
		assertEquals("Total read must be same as produced elements on multi-threding except 10000 rejected ones", 
				81000, readCount.get());
		assertEquals("Total rejected must be 10000 elements on multi-threding", 
				19000, rejectCount.get());
		channel.removeChannelInputListener(listener);
		channel.removeFilter(filter);
	}
	private static final String intToLineString(Integer i) {
		return "Line " + i;
	}
	
	private List<String> produceTestData(int numberOfLines) {
		return IntStream.range(0, numberOfLines)
		.mapToObj( ChannelUnitTest::intToLineString )
		.collect(Collectors.toList());
	}
	
	private <T> void runThreadsOnProducer(StreamWriter<String> producer, int numberOfLines, int numberOfProcesses) {
		ExecutorService executor = Executors.newCachedThreadPool();
		  for (int i = 0; i < numberOfProcesses; i++) executor.execute(() -> {
			  try {
				producer.write(produceTestData(numberOfLines));
			} catch (Exception e) {
				e.printStackTrace();
			}
		  });
	}

	private <T> void runThreadsOnProducerForArray(StreamWriter<String> producer, int numberOfLines, int numberOfProcesses) {
		ExecutorService executor = Executors.newCachedThreadPool();
		  for (int i = 0; i < numberOfProcesses; i++) executor.execute(() -> {
			  try {
				  List<String> list = produceTestData(numberOfLines);
				producer.write(list.toArray(new String[list.size()]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		  });
	}
}
