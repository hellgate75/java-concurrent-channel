/**
 * 
 */
package com.java.concurrent.utils.streams.channel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.java.concurrent.utils.streams.channel.Channel;
import com.java.concurrent.utils.streams.channel.ChannelsRegistry;
import com.java.concurrent.utils.streams.channel.exceptions.ChannelIOException;
import com.java.concurrent.utils.streams.channel.exceptions.ChannelInstanceException;
import com.java.concurrent.utils.streams.channel.exceptions.ChannelNullableAssignementException;
import com.java.concurrent.utils.streams.channel.exceptions.DuplicatedObjectException;
import com.java.concurrent.utils.streams.channel.listeners.ChannelInputListener;
import com.java.concurrent.utils.streams.channel.listeners.ChannelOutputListener;
import com.java.concurrent.utils.streams.channel.operators.ChannelConsumer;
import com.java.concurrent.utils.streams.channel.operators.ChannelFilter;
import com.java.concurrent.utils.streams.channel.operators.ChannelProducer;

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
	public void testStandAloneChannelInstance() throws ChannelInstanceException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		ChannelsRegistry.get().registerChannel(channel, "MyStandAloneChannel");
		assertEquals("StandAlone manual opted-in Channel in Registry must match", channel, ChannelsRegistry.get().getChannel("MyStandAloneChannel"));
		assertNull("Class Name Must be null", channel.getChannelName());
		assertTrue("Channel is Empty", channel.isEmpty());
	}
	
	@Test(expected=ChannelNullableAssignementException.class)
	public void testStandAloneChannelAddSingleNullableAssignementException() throws ChannelNullableAssignementException, ChannelIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String value=null;
		channel.add(value);
	}
	
	@Test(expected=ChannelIOException.class)
	public void testStandAloneChannelAddSingleChannelIOException() throws ChannelNullableAssignementException, ChannelIOException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		ChannelFilter<String> filter = new ChannelFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				if (t==null || t.trim().isEmpty())
					throw new NullPointerException("Value not nullable");
				return !t.contains("9");
			}
		};
		channel.addChannelFilter(filter);
		String value="";
		channel.add(value);
		
	}
	
	@Test(expected=ChannelNullableAssignementException.class)
	public void testStandAloneChannelAddListNullableAssignementException() throws ChannelNullableAssignementException, ChannelIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		List<String> valueList = null;
		channel.add(valueList);
		
	}
	
	@Test(expected=ChannelIOException.class)
	public void testStandAloneChannelAddListChannelIOException() throws ChannelNullableAssignementException, ChannelIOException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		ChannelFilter<String> filter = new ChannelFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				if (t==null || t.trim().isEmpty())
					throw new NullPointerException("Value not nullable");
				return !t.contains("9");
			}
		};
		channel.addChannelFilter(filter);
		String value=null;
		List<String> valueList = new ArrayList<String>(0);
		valueList.add(value);
		channel.add(valueList);
	}
	
	@Test(expected=ChannelNullableAssignementException.class)
	public void testStandAloneChannelAddArrayNullableAssignementException() throws ChannelNullableAssignementException, ChannelIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String[] value=null;
		channel.add(value);
		
	}
	
	@Test(expected=ChannelIOException.class)
	public void testStandAloneChannelAddArrayChannelIOException() throws ChannelNullableAssignementException, ChannelIOException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		ChannelFilter<String> filter = new ChannelFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				if (t==null || t.trim().isEmpty())
					throw new NullPointerException("Value not nullable");
				return !t.contains("9");
			}
		};
		channel.addChannelFilter(filter);
		String value=null;
		channel.add(new String[] {value});
	}
	
	@Test()
	public void testStandAloneChannelPoll() throws ChannelIOException, DuplicatedObjectException, ChannelNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String value="A";
		channel.add(new String[] {value});
		assertEquals("Polled value must be same than the added one","A", channel.poll());
		assertTrue("Channel is Empty", channel.isEmpty());
	}
	
	@Test(expected=ChannelIOException.class)
	public void testStandAloneChannelPollChannelIOException() throws ChannelIOException, DuplicatedObjectException, ChannelNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		assertTrue("Channel is Empty", channel.isEmpty());
		assertEquals("Polled value must be same than the added one","A", channel.poll());
	}
	
	@Test()
	public void testStandAloneChannelRetainAll() throws ChannelIOException, DuplicatedObjectException, ChannelNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		String value="A";
		channel.add(new String[] {value});
		List<String> retainList = new ArrayList<>(0);
		retainList.add("B");
		channel.retainAll(retainList);
		assertTrue("Channel is Empty", channel.isEmpty());
	}
	
	@Test
	public void testRegisteredChannelInstance() throws ChannelInstanceException, DuplicatedObjectException {
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

	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelProducerChannelNullableAssignementException() throws ChannelNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addChannelConsumer(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelProducerChannelDuplicatedObjectException() throws ChannelNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		ChannelConsumer<String> consumer = new ChannelConsumer<String>() {
			
			@Override
			public void consume(String t) {
				LOGGER.warn("Consumed: " + t);
			}
		};
		channel.addChannelConsumer(consumer);
		channel.addChannelConsumer(consumer);
		
	}

	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelProducerRemoveChannelNullableAssignementException() throws ChannelNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeChannelConsumer(null);
		
	}

	@Test(timeout=15000)
	public void testChannelProducerAndConsume() throws ChannelNullableAssignementException, DuplicatedObjectException, ChannelIOException {
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
		ChannelConsumer<String> consumer = new ChannelConsumer<String>() {
			
			@Override
			public void consume(String t) {
				LOGGER.warn("Consumed: " + t);
			}
		};
		channel.addChannelConsumer(consumer);
		channel.start();
		assertTrue("Consumer Thread Must be running", channel.running());
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		channel.stop();
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		assertEquals("Total consumed elements must same than produced ones", 
				channel.getTotalElements(), channel.getTotalConsumedElements());
		channel.removeChannelConsumer(consumer);
		assertTrue("Channel is Empty", channel.isEmpty());
	}

	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelInputListenerChannelNullableAssignementException() throws ChannelNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addChannelInputListener(null);
		
	}

	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelInputListenerRemoveChannelNullableAssignementException() throws ChannelNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeChannelInputListener(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelInputListenerChannelDuplicatedObjectException() throws ChannelNullableAssignementException, DuplicatedObjectException {
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
	public void testChannelInputListener() throws ChannelNullableAssignementException, DuplicatedObjectException, ChannelIOException {
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

	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelOutputListenerChannelNullableAssignementException() throws ChannelNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addChannelOutputListener(null);
		
	}

	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelOutputListenerRemoveChannelNullableAssignementException() throws ChannelNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeChannelOutputListener(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelOutputListenerChannelDuplicatedObjectException() throws ChannelNullableAssignementException, DuplicatedObjectException {
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
	public void testChannelOutputListener() throws ChannelNullableAssignementException, DuplicatedObjectException, ChannelIOException {
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
		channel.addChannelConsumer(new ChannelConsumer<String>() {
			
			@Override
			public void consume(String t) {
				// Only for compliance to writer process
				// This is not part of test
			}
		});
		channel.start();
		assertTrue("Consumer Thread Must be running", channel.running());
		try {
			// Delay that allows Channel to flush all pending elements 
			// into the Channel Concurrent Queue
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		channel.stop();
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
	
	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelFilterChannelNullableAssignementException() throws ChannelNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.addChannelFilter(null);
		
	}

	@Test(expected=ChannelNullableAssignementException.class)
	public void testChannelFilterRemoveChannelNullableAssignementException() throws ChannelNullableAssignementException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		channel.removeChannelFilter(null);
		
	}

	@Test(expected=DuplicatedObjectException.class)
	public void testChannelFilterChannelDuplicatedObjectException() throws ChannelNullableAssignementException, DuplicatedObjectException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		ChannelFilter<String> filter = new ChannelFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				return !t.contains("9");
			}
		};
		channel.addChannelFilter(filter);
		channel.addChannelFilter(filter);
	}

	@Test(timeout=8000)
	public void testChannelFilter() throws ChannelNullableAssignementException, DuplicatedObjectException, ChannelIOException {
		Channel<String> channel = new Channel<>(String.class);
		assertEquals("Class Type Must match", String.class, channel.getType());
		AtomicLong readCount = new AtomicLong(0L);
		AtomicLong rejectCount = new AtomicLong(0L);
		ChannelFilter<String> filter = new ChannelFilter<String>() {
			
			@Override
			public boolean accept(String t) {
				return !t.contains("9");
			}
		};
		channel.addChannelFilter(filter);
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
		channel.removeChannelFilter(filter);
	}
	private static final String intToLineString(Integer i) {
		return "Line " + i;
	}
	
	private List<String> produceTestData(int numberOfLines) {
		return IntStream.range(0, numberOfLines)
		.mapToObj( ChannelUnitTest::intToLineString )
		.collect(Collectors.toList());
	}
	
	private <T> void runThreadsOnProducer(ChannelProducer<String> producer, int numberOfLines, int numberOfProcesses) {
		ExecutorService executor = Executors.newCachedThreadPool();
		  for (int i = 0; i < numberOfProcesses; i++) executor.execute(() -> {
			  try {
				producer.add(produceTestData(numberOfLines));
			} catch (Exception e) {
				e.printStackTrace();
			}
		  });
	}

	private <T> void runThreadsOnProducerForArray(ChannelProducer<String> producer, int numberOfLines, int numberOfProcesses) {
		ExecutorService executor = Executors.newCachedThreadPool();
		  for (int i = 0; i < numberOfProcesses; i++) executor.execute(() -> {
			  try {
				  List<String> list = produceTestData(numberOfLines);
				producer.add(list.toArray(new String[list.size()]));
			} catch (Exception e) {
				e.printStackTrace();
			}
		  });
	}
}
