package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

// @EnableBinding(Sink.class)
public class Receiver {
	private static Logger logger = LoggerFactory.getLogger(Receiver.class);

	@StreamListener(Sink.INPUT)
	public void receive(Object payload) {
		logger.info("Received: " + payload);
	}
}
