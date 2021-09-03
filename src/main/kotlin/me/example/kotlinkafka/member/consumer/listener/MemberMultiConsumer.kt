//package me.example.kotlinkafka.member.consumer.listener
//
//import org.springframework.kafka.annotation.KafkaHandler
//import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
//import org.springframework.kafka.support.KafkaHeaders
//import org.springframework.messaging.handler.annotation.Header
//import org.springframework.stereotype.Component
//
///**
// * Created by LYT to 2021/09/01
// */
//@Component
//@KafkaListener(id = "mem1", topics = ["insert_member"])
//class MemberMultiConsumer {
//
//    @KafkaHandler(isDefault = true)
//    fun listen1(
//        data: String,
//        @Header(KafkaHeaders.RECORD_METADATA) meta: ConsumerRecordMetadata
//    ) {
//        println("""
//            [listen1]
//            data:: $data
//            topic:: ${meta.topic()}
//            partition:: ${meta.partition()}
//            offset:: ${meta.offset()}
//        """.trimIndent())
//    }
//
//    @KafkaHandler
//    fun listen2(data: Int) {
//        println("[listen2] data:: $data")
//    }
//
////    @KafkaHandler
////    fun listen3(data: Any) {
////        println("[listen3] data:: $data")
////    }
//
//}
//
