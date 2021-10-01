package me.example.kotlinkafka.common.kafka.producer.config

import me.example.kotlinkafka.member.domain.dto.Member
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer
import java.io.Serializable

/**
 * Created by LYT to 2021/09/30
 */
@Configuration
@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "default")
class KafkaMemberProducerConfig {

    @Value("\${spring.kafka.producer.bootstrap-servers}")
    private lateinit var BOOTSTRAP_SERVER: String

    @Bean
    fun memberKafkaTemplate(): KafkaTemplate<String, Member> {
        val factory = DefaultKafkaProducerFactory<String, Member>(productIntConfigs())
        return KafkaTemplate(factory)
    }

    fun productIntConfigs(): Map<String, Serializable> =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )

}