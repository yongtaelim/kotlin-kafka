package me.example.kotlinkafka.member.consumer.listener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.PartitionOffset
import org.springframework.kafka.annotation.TopicPartition
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload

/**
 * Created by LYT to 2021/08/24
 */
//@Component
class MemberTestConsumer {

    @KafkaListener(id = "mem", topics = ["insert_member"], clientIdPrefix = "memClientId")
    fun listen(data: String) {
        println("data:: $data")
    }

    @KafkaListener(
        id = "mem",
        topics = ["insert_member"],
        autoStartup = "\${listen.auto.start:true}",
        concurrency = "\${listen.concurrency:3}"
    )
    fun listen1(data: String) {
        println("data:: $data")
    }

    @KafkaListener(
        id = "thing2",
        topicPartitions = [
            TopicPartition(topic = "insert_member", partitions = ["0"])
        ]
    )
    fun listen2(data: String) {
        println("data:: $data")
    }

    @KafkaListener(
        id = "mem",
        topicPartitions = [
            TopicPartition(
                topic = "insert_member",
                partitions = ["0"],
                partitionOffsets = [PartitionOffset(partition = "*", initialOffset = "0")]
            )
        ]
    )
    fun listen3(data: String) {
        println("data:: $data")
    }

    @KafkaListener(id = "mem", topics = ["insert_member"], containerFactory = "kafkaListenerContainerFactory")
    fun listen(data: String, ack: Acknowledgment) {
        println("data:: $data")
        ack.acknowledge()
    }

    @KafkaListener(id = "mem", topicPattern = "insert_member")
    fun listen(
        @Payload data: String,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) key: Int?,
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) partition: Int,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) ts: Long
    ) {
        println("data:: $data key:: $key partition:: $partition topic:: $topic ts:: $ts")
    }

    @KafkaListener(id = "mem", topicPattern = "insert_member")
    fun listen(data: String, meta: ConsumerRecordMetadata) {
        println("data:: $data partition:: ${meta.partition()} topic:: ${meta.topic()} ts:: ${meta.timestamp()}")
    }
}