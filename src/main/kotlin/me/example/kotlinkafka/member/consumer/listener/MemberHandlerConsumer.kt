package me.example.kotlinkafka.member.consumer.listener

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

/**
 * Created by LYT to 2021/09/24
 */
//@Component
//@KafkaListener(id = "yongKafkaHandler", topics = ["insert_member"])
class MemberHandlerConsumer {

//    @KafkaHandler
//    fun listener1(foo: String) {
//        println("listener1 data:: $foo")
//    }

    @KafkaHandler
    fun listener2(bar: Int) {
        println("listener2 data:: $bar")
    }

//    @KafkaHandler(isDefault = true)
//    fun listener3(any: Any) {
//        println("listener3 data:: $any")
//    }

}