package com.kafka;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class SenderAvro {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "1");
        properties.put("retries", "10");
        properties.put("key.serializer", LongSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", "http://localhost:8081");

        KafkaProducer<Long, Customer> kafkaProducer = new KafkaProducer(properties);

        String topic = "customers";
        Customer customer = Customer.newBuilder()
                .setFirstName("Piotr")
                .setLastName("Kapcia")
                .setAge(32)
                .setHeight(183)
                .setWeight(86)
                .setAutomatedEmail(true)
                .build();

        ProducerRecord<Long, Customer> producerRecord = new ProducerRecord<>(topic, customer);
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
