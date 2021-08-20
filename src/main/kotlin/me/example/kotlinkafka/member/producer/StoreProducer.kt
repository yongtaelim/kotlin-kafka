//package me.example.kotlinkafka.member.producer
//
//import me.example.kotlinkafka.common.kafka.producer.KafkaProducer
//import org.apache.kafka.clients.producer.ProducerRecord
//import org.springframework.boot.ApplicationRunner
//import org.springframework.context.annotation.Bean
//import org.springframework.kafka.core.RoutingKafkaTemplate
//import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
//import org.springframework.stereotype.Component
//import java.util.concurrent.TimeUnit
//
///**
// * Created by LYT to 2021/08/18
// */
//@Component
//class StoreProducer(
//    val template: RoutingKafkaTemplate
//): KafkaProducer() {
//
//    override fun sendMessage(message: String) {
//        template.send("one", message)
//        template.send("two", message.toByteArray())
//    }
//
//}