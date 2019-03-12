package com.kafka;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SenderAvro {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.l:9092");
        properties.put("acks", "1");
        properties.put("retries", "10");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", "http://127.0.0.1:8081");

        KafkaProducer<String, Customer> kafkaProducer = new KafkaProducer<String, Customer>(properties);

        String topic = "customers";
        Customer customer = Customer.newBuilder()
                .setFirstName("Piotr")
                .setLastName("Kapcia")
                .setAge(32)
                .setHeight(183)
                .setWeight(86)
                .setAutomatedEmail(true)
                .build();

        ProducerRecord<String, Customer> producerRecord = new ProducerRecord<>(topic, customer);
        kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
            if (e == null) {
                System.out.println("Success");
                System.out.println(recordMetadata.toString());
            } else {
                e.printStackTrace();
            }
        });
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
