package me.example.kotlinkafka.common.kafka.producer.config

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import java.io.Serializable


/**
 * Created by LYT to 2021/08/18
 */
@Configuration
@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "replying")
class ReplyingTemplateConfig {

    @Value("\${spring.kafka.producer.bootstrap-servers}")
    private lateinit var BOOTSTRAP_SERVER: String

    @Bean
    fun replyingTemplate(
        pf: ProducerFactory<String, String>,
        repliesContainer: ConcurrentMessageListenerContainer<String, String>
    ): ReplyingKafkaTemplate<String, String, String> {
        return ReplyingKafkaTemplate(pf, repliesContainer)
    }

    @Bean
    fun repliesContainer(containerFactory: ConcurrentKafkaListenerContainerFactory<String, String>): ConcurrentMessageListenerContainer<String, String> {
        val repliesContainer = containerFactory.createContainer("replies1")
        repliesContainer.containerProperties.setGroupId("replies1.reliesGroup")
//        repliesContainer.isAutoStartup = false
        return repliesContainer
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        val factory = DefaultKafkaProducerFactory<String, String>(producerConfigs())
        return KafkaTemplate(factory)
    }

    fun producerConfigs(): Map<String, Serializable> =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
}