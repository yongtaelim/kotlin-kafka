//package me.example.kotlinkafka.member.producer
//
//import me.example.kotlinkafka.common.kafka.producer.KafkaProducer
//import org.springframework.kafka.core.KafkaTemplate
//import org.springframework.stereotype.Component
//
///**
// * Created by LYT to 2021/08/17
// */
//@Component
//class MemberSingleProducer(
//    val kafkaTemplate: KafkaTemplate<String, String>
//): KafkaProducer() {
//    companion object {
//        const val TOPIC_NAME = "save_single_member"
//    }
//
//    /**
//     * async send message
//     * @param message String
//     */
//    override fun sendMessage(message: String) {
//        // kafka producer
//        val listenableFuture = kafkaTemplate.send(TOPIC_NAME, message)
//
//        // kafka add callback
//        listenableFuture.addCallback(listenableFutureCallback(message))
//    }
//
//}