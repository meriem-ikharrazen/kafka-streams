package org.example;

import org.example.producer.KafkaStreamProducer;

public class Main {
    public static void main(String[] args) {
        new KafkaStreamProducer().start();
    }
}