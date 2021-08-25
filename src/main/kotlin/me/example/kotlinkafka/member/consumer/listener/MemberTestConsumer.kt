package me.example.kotlinkafka.member.consumer.listener

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.PartitionOffset
import org.springframework.kafka.annotation.TopicPartition
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
    fun listen(data: String) {
        println("data:: $data")
    }

}