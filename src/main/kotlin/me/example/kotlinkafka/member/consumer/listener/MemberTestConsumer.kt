package me.example.kotlinkafka.member.consumer.listener

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.protocol.Message
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.PartitionOffset
import org.springframework.kafka.annotation.TopicPartition
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
import org.springframework.kafka.support.Acknowledgment
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

/**
 * Created by LYT to 2021/08/24
 */
@Component
class MemberTestConsumer {

//    @KafkaListener(id = "mem", topics = ["insert_member"], clientIdPrefix = "memClientId")
//    fun listen(data: String) {
//        println("data:: $data")
//    }

//    @KafkaListener(
//        id = "mem",
//        topics = ["insert_member"],
//        autoStartup = "\${listen.auto.start:true}",
//        concurrency = "\${listen.concurrency:3}"
//    )
//    fun listen(data: String) {
//        println("data:: $data")
//    }

//    @KafkaListener(
//        id = "thing2",
//        topicPartitions = [
//            TopicPartition(topic = "insert_member", partitions = ["0"])
//        ]
//    )
//    fun listen(data: String) {
//        println("data:: $data")
//    }

//    @KafkaListener(
//        id = "mem",
//        topicPartitions = [
//            TopicPartition(
//                topic = "insert_member",
//                partitions = ["0"],
//                partitionOffsets = [PartitionOffset(partition = "*", initialOffset = "0")]
//            )
//        ]
//    )
//    fun listen(data: String) {
//        println("data:: $data")
//    }

//    @KafkaListener(id = "mem", topics = ["insert_member"], containerFactory = "kafkaListenerContainerFactory")
//    fun listen(data: String, ack: Acknowledgment) {
//        println("data:: $data")
//        ack.acknowledge()
//    }

//    @KafkaListener(id = "mem", topicPattern = "insert_member")
//    fun listen(
//        @Payload data: String,
//        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) key: Int?,
//        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) partition: Int,
//        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
//        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) ts: Long
//    ) {
//        println("data:: $data key:: $key partition:: $partition topic:: $topic ts:: $ts")
//    }

//    @KafkaListener(id = "mem", topicPattern = "insert_member")
//    fun listen(data: String, meta: ConsumerRecordMetadata) {
//        println("data:: $data partition:: ${meta.partition()} topic:: ${meta.topic()} ts:: ${meta.timestamp()}")
//    }

//    @KafkaListener(id = "listenBatch1", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group1")
//    fun listenBatch1(data: List<String>) {
//        println("batch1 data:: $data")
//    }
//
//    @KafkaListener(id = "listenBatch2", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group1")
//    fun listenBatch2(data: List<Message>?) {
//        println("batch2 data:: $data")
//    }
//
//    @KafkaListener(id = "listenBatch3", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group1")
//    fun listenBatch3(data: List<Message>?, ack: Acknowledgment) {
//        println("batch3 data:: $data")
//    }
//
//    @KafkaListener(id = "listenBatch4", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group2")
//    fun listenBatch4(data: List<Message>?, ack: Acknowledgment, consumer: Consumer<String, String>) {
//        println("batch4 data:: $data")
//    }
//
//    @KafkaListener(id = "listenBatch5", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group2")
//    fun listenBatch5(data: List<ConsumerRecord<String, String>>) {
//        data.forEach {
//            println("batch5 data:: ${it.value()} topic=${it.topic()} partition=${it.partition()} offset=${it.offset()}")
//        }
//
//    }
//
//    @KafkaListener(id = "listenBatch6", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group2")
//    fun listenBatch6(data: List<ConsumerRecord<String, String>>, ack: Acknowledgment) {
//        data.forEach {
//            println("batch6 data:: ${it.value()} topic=${it.topic()} partition=${it.partition()} offset=${it.offset()}")
//        }
//    }
//
//    @KafkaListener(id = "listener1", topics = ["insert_member"], groupId = "group1", autoStartup = "false")
//    fun listener1(data: String) {
//        println("listener1 data:: $data")
//    }
//
//    @KafkaListener(id = "listener2", topics = ["insert_member"], groupId = "group1")
//    fun listener2(data: String) {
//        println("listener2 data:: $data")
//    }
//
//    @KafkaListener(id = "listener3", topics = ["insert_member"], groupId = "group2")
//    fun listener3(data: String) {
//        println("listener3 data:: $data")
//    }
//
//    @KafkaListener(id = "listener4", topics = ["insert_member"], groupId = "group2")
//    fun listener4(data: String) {
//        println("listener4 data:: $data")
//    }
}