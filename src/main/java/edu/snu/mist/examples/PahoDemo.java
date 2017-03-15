package edu.snu.mist.examples;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoDemo implements MqttCallback {

  private final long iterations = 100000L;

  private MqttClient client;

  public PahoDemo() {
  }

  public static void main(final String[] args) {
    new PahoDemo().doDemo();
  }

  public void doDemo() {

    String topic = "MQTT Examples";
    int qos = 0;
    String broker = "tcp://localhost:1883";
    String brokerClientId = "ExampleBrokerClient";
    MemoryPersistence memoryPersistence = new MemoryPersistence();

    try {
      client = new MqttClient(broker, brokerClientId, memoryPersistence);
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      client.connect(connOpts);
      client.setCallback(this);
      client.subscribe(topic);
      for(int i = 0; i < iterations; i++) {
        final String clientId = "Publisher-" + i;
        MqttClient innerClient = new MqttClient(broker, clientId, memoryPersistence);
        innerClient.connect();
        innerClient.setCallback(this);
        MqttMessage message = new MqttMessage();
        message.setPayload(("This is message from Publisher-" + i)
            .getBytes());
        message.setQos(qos);
        innerClient.publish(topic, message);
      }
    } catch (MqttException e) {
      System.out.println("reason "+e.getReasonCode());
      System.out.println("msg "+e.getMessage());
      System.out.println("loc "+e.getLocalizedMessage());
      System.out.println("cause "+e.getCause());
      System.out.println("excep "+e);
      e.printStackTrace();
    }
  }

  @Override
  public void connectionLost(final Throwable cause) {
    // TODO Auto-generated method stub

  }

  @Override
  public void messageArrived(final String topic, final MqttMessage message)
      throws Exception {
    System.out.println(message);
  }

  @Override
  public void deliveryComplete(final IMqttDeliveryToken token) {
    // TODO Auto-generated method stub

  }

}