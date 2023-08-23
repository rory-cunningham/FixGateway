package com.fix.gateway;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import quickfix.*;

/**
 * @author Rory Cunningham
 */

@Component
@Builder
public class QuickFixGateway extends MessageCracker implements Application {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private KafkaProducer kafkaProducer;
    @Override
    public void onCreate(SessionID sessionID) {
        //This method is called when quickfix creates a new session.
        log.info("New session created");
    }

    @Override
    public void onLogon(SessionID sessionID) {
        // This callback notifies you when a valid logon has been established with a counter party.
        log.info("Successful connection with CounterParty");
    }

    @Override
    public void onLogout(SessionID sessionID) {
        //This callback notifies you when an FIX session is no longer online.
        log.info("Successful disconnection with Counterparty");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        // This callback provides you with a peek at the administrative messages that are being sent from your FIX
        // engine to the counter party.
        log.info("Admin Message:" + message);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        //This callback notifies you when an administrative message is sent from a counterparty to your FIX engine.
        log.info("Admin message from counterparty" + message);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        // This is a callback for application messages that you are sending to a counterparty.
    }

    @Override
    public void fromApp(Message message, SessionID sessionID)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        // This is a callback for application messages that you are receiving from a counterparty.
        log.info("Received message from counterparty" + message);
        crack(message, sessionID);
    }

    @Override
    public void onMessage(Message message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        // This is a callback for application messages that you are receiving from a counterparty.
        log.info("Received message from counterparty" + message);
        kafkaProducer.send("topic",message);
    }
}
