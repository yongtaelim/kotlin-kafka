//package me.example.kotlinkafka.common.kafka.consumer.config
//
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.clients.consumer.ConsumerRecord
//import org.apache.kafka.common.serialization.StringDeserializer
//import org.apache.logging.log4j.LogManager
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.annotation.EnableKafka
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory
//import org.springframework.kafka.listener.AcknowledgingMessageListener
//import org.springframework.kafka.listener.ContainerProperties
//import org.springframework.kafka.listener.KafkaMessageListenerContainer
//import org.springframework.kafka.listener.MessageListener
//import org.springframework.kafka.support.Acknowledgment
//import org.springframework.kafka.support.LogIfLevelEnabled
//
///**
// * Created by LYT to 2021/08/20
// *
// * It receives a ConsumerFactory and information about topics and partitions
// * as well as other configuration, in a ContainerProperties object.
// */
//@EnableKafka
//@Configuration
//class KafkaSingleConsumerConfig {
//
//    val log = LogManager.getLogger()
//
//    @Value("\${spring.kafka.consumer.bootstrap-servers}")
//    private lateinit var BOOTSTRAP_SERVER: String
//
//    @Bean
//    fun kafkaMessageListenerContainer(): KafkaMessageListenerContainer<String, String> {
//        val props = ContainerProperties("save_member")
//        props.messageListener = getMessageListener()
//        props.commitLogLevel = LogIfLevelEnabled.Level.DEBUG
//        props.setGroupId("test11")
//
//        val cf = DefaultKafkaConsumerFactory<String, String>(getConfig())
//        return KafkaMessageListenerContainer(cf, props)
//    }
//
//    private fun getMessageListener(): MessageListener<String, String> {
//        val messageListener =
////            MessageListener<String, String>() {
////                log.info("Consumer Recode. Value=${it.value()} Offset=${it.offset()}")
////            }
//
//            AcknowledgingMessageListener<String, String> { data, acknowledgment ->
//                log.info("Consumer Recode. Value=${data.value()} Offset=${data.offset()}")
//                Thread.sleep(2000)
//                acknowledgment?.acknowledge()
//            }
//        return messageListener
//    }
//
//    private fun getConfig(): Map<String, Any> =
//        mapOf(
//            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
////            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
//            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",
//            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
//            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
//        )
//
//
//}