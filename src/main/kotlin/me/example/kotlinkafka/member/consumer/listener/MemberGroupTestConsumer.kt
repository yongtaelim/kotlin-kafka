package me.example.kotlinkafka.member.consumer.listener

import org.springframework.kafka.annotation.KafkaListener

/**
 * Created by LYT to 2021/08/24
 */
//@Component
class MemberGroupTestConsumer {

    @KafkaListener(id = "listener1", topics = ["insert_member"], groupId = "group1", autoStartup = "false")
    fun listener1(data: String) {
        println("listener1 data:: $data")
    }

    @KafkaListener(id = "listener2", topics = ["insert_member"], groupId = "group1")
    fun listener2(data: String) {
        println("listener2 data:: $data")
    }

    @KafkaListener(id = "listener3", topics = ["insert_member"], groupId = "group2")
    fun listener3(data: String) {
        println("listener3 data:: $data")
    }

    @KafkaListener(id = "listener4", topics = ["insert_member"], groupId = "group2")
    fun listener4(data: String) {
        println("listener4 data:: $data")
    }
}