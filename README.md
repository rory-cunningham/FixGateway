WIP

Fix Gateway is a Spring Boot based FIX protocol message gateway. 
It segregates messages from incoming sessions and passes them into appropriate Kafka Topics to be consumed by a client.

TODO:
  Implement ThreadedSocketAcceptor to create new threads for each session
  Handle all Fix Message types, including garbled messages
  
