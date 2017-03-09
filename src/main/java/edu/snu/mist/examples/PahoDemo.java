package edu.snu.mist.examples;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PahoDemo implements MqttCallback {

  private final long iterations = 1000L;

  private MqttClient client;

  public PahoDemo() {
  }

  public static void main(final String[] args) {
    new PahoDemo().doDemo();
  }

  public void doDemo() {
    try {
      client = new MqttClient("tcp://localhost:1883", "Subscriber");
      client.connect();
      client.setCallback(this);
      client.subscribe("foo");
      for(int i = 0; i < iterations; i++) {
        final String clientId = "Publisher-" + i;
        MqttClient innerClient = new MqttClient("tcp://localhost:1883", clientId);
        innerClient.connect();
        innerClient.setCallback(this);
        MqttMessage message = new MqttMessage();
        message.setPayload(("This is message from Publisher-" + i)
            .getBytes());
        innerClient.publish("foo", message);
      }
    } catch (MqttException e) {
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