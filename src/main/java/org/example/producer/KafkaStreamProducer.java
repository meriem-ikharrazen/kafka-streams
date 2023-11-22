package org.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KafkaStreamProducer {
    String message;
    Random random=new Random();

    public static void main(String[] args) {
        new KafkaStreamProducer().start();
    }


    /*
        Produire des message random
     */

    public void start() {
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //key value de type string
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //spec du client id
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "streams-prod-1");

        KafkaProducer<String,String> kafkaProducer =new KafkaProducer<String,String>(properties);

        List<Character> characters=new ArrayList<>();

        for(char c='A'; c<'Z';c++)characters.add(c);

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
            message="";
            for(int i=0;i<10;i++)message+=" "+characters.get(random.nextInt(characters.size()));
            //j'envoie ça sur un Topic
            kafkaProducer.send(new ProducerRecord<String,String>("kafkaTopic",null,message),(md,ex)->{
                System.out.println("Sending Message "+message+" To Topic "+md.topic()+" Partition "+md.partition());
            });
        },1000,1000, TimeUnit.MILLISECONDS);
    }

}
