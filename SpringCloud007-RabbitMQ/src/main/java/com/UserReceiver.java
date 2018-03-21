package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.domain.User;

@EnableBinding(Sink.class)
public class UserReceiver {
	private static Logger logger = LoggerFactory.getLogger(UserReceiver.class);

	@StreamListener(Sink.INPUT)
    public void receive(User user) {
        logger.info("Received: " + user);
    }
}
