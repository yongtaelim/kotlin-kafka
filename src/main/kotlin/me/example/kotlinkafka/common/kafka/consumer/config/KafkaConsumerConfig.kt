//package me.example.kotlinkafka.common.kafka.consumer.config
//
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.common.serialization.StringDeserializer
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.annotation.EnableKafka
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory
//import org.springframework.kafka.listener.ContainerProperties
//
///**
// * Created by LYT to 2021/08/17
// */
//@EnableKafka
//@Configuration
//@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "default")
//class KafkaConsumerConfig(
////    private val validator: LocalValidatorFactoryBean
//) {
////    : KafkaListenerConfigurer {
//
//    @Value("\${spring.kafka.consumer.bootstrap-servers}")
//    private lateinit var BOOTSTRAP_SERVER: String
//
////    @Bean
////    fun saveMemberKafkaListener(): ConcurrentKafkaListenerContainerFactory<String, String> =
////        kafkaListenerContainerFactory()
//
//    @Bean
//    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
//        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
//
//        factory.setConcurrency(3) // Consumer Process Thread Count
//        factory.consumerFactory = DefaultKafkaConsumerFactory(getConfig())
//        factory.containerProperties.pollTimeout = 500
//        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
//
//        return factory
//    }
//
//    private fun getConfig(): Map<String, Any> =
//        mapOf(
//            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
//            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",    // 마지막 읽은 부분부터 Read
//            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
//            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
//        )
//
////    override fun configureKafkaListeners(registrar: KafkaListenerEndpointRegistrar?) {
////        registrar?.setValidator(validator)
////    }
//}