package com.fix.gateway;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quickfix.*;
import java.io.IOException;

/**
 * @author Rory Cunningham
 */

@SpringBootApplication
public class FixGatewayApplication {

	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FixGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FixGatewayApplication.class, args);

		try{
			Application app = QuickFixGateway.builder().build();
			SessionSettings settings = new SessionSettings("src/main/Resources/OrderApp.cfg");
			MessageStoreFactory storeFactory = new FileStoreFactory(settings);
			LogFactory logFactory = new FileLogFactory(settings);
			MessageFactory messageFactory = new DefaultMessageFactory();
			startAcceptor(app, settings, storeFactory, logFactory, messageFactory);
		}catch (Exception e){LOGGER.error(e.getMessage());}

	}

	private static void startAcceptor(Application app, SessionSettings settings, MessageStoreFactory storeFactory,
									  LogFactory logFactory, MessageFactory messageFactory) throws ConfigError, IOException {
		ThreadedSocketAcceptor acceptor = new ThreadedSocketAcceptor(app, storeFactory, settings, logFactory, messageFactory);
		acceptor.start();
		LOGGER.info("press <enter> to quit");
		System.in.read();
		acceptor.stop();
	}

}
