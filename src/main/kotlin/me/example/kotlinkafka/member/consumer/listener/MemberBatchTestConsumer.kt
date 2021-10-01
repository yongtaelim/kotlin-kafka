package me.example.kotlinkafka.member.consumer.listener

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.protocol.Message
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment

/**
 * Created by LYT to 2021/08/24
 */
//@Component
class MemberBatchTestConsumer {

    @KafkaListener(id = "listenBatch1", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group1")
    fun listenBatch1(data: List<String>) {
        println("batch1 data:: $data")
    }

    @KafkaListener(id = "listenBatch2", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group1")
    fun listenBatch2(data: List<Message>?) {
        println("batch2 data:: $data")
    }

    @KafkaListener(id = "listenBatch3", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group1")
    fun listenBatch3(data: List<Message>?, ack: Acknowledgment) {
        println("batch3 data:: $data")
    }

    @KafkaListener(id = "listenBatch4", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group2")
    fun listenBatch4(data: List<Message>?, ack: Acknowledgment, consumer: Consumer<String, String>) {
        println("batch4 data:: $data")
    }

    @KafkaListener(id = "listenBatch5", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group2")
    fun listenBatch5(data: List<ConsumerRecord<String, String>>) {
        data.forEach {
            println("batch5 data:: ${it.value()} topic=${it.topic()} partition=${it.partition()} offset=${it.offset()}")
        }

    }

    @KafkaListener(id = "listenBatch6", topics = ["insert_member"], containerFactory = "batchFactory", groupId = "group2")
    fun listenBatch6(data: List<ConsumerRecord<String, String>>, ack: Acknowledgment) {
        data.forEach {
            println("batch6 data:: ${it.value()} topic=${it.topic()} partition=${it.partition()} offset=${it.offset()}")
        }
    }
}