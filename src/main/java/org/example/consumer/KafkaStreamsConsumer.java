package org.example.consumer;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class KafkaStreamsConsumer {

    public static void main(String[] args) {
        new KafkaStreamsConsumer().start();
    }

    private void start() {
        Properties properties=new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-consumer6ID-1");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass().getName());
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,1000);

        //
        StreamsBuilder streamsBuilder=new StreamsBuilder();
        //Pour dire que les données que tu vas consommer seront key de type string et value de type string
        KStream<String,String> kstream=streamsBuilder.stream("kafkaTopic", Consumed.with(Serdes.String(), Serdes.String()));

        kstream.foreach((k,v)->{
            System.out.println(k+"=>"+v);
        });

        //démarrer kafka streams
        Topology topology=streamsBuilder.build();
        KafkaStreams kafkaStreams=new KafkaStreams(topology,properties);
        kafkaStreams.start();

    }
}
