package me.example.kotlinkafka.common.kafka.producer.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.core.RoutingKafkaTemplate
import java.io.Serializable
import java.util.regex.Pattern

/**
 * Created by LYT to 2021/08/18
 */
@Configuration
@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "routing")
class RoutingTemplateConfig {

    @Value("\${spring.kafka.producer.bootstrap-servers}")
    private lateinit var BOOTSTRAP_SERVER: String

    @Bean
    fun kafkaTemplate(): KafkaTemplate<Any, Any> {
        val factory = DefaultKafkaProducerFactory<Any, Any>(producerConfigs())
        return KafkaTemplate(factory)
    }

    fun producerConfigs(): Map<String, Serializable> =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )

    /**
     * Version 2.5 After
     *
     * 항목 이름에 따라 런타임에 생산자를 선택할 수 있다.
     *   - one:: StringSerializer
     *   - two:: ByteArraySerializer
     *
     * Transactions 은 Support 하지 않는다. execute, flush, metrics의 항목을 알 수 없기 때문에 작업 수행 불가
     */
    @Bean
    fun routingTemplate(context: GenericApplicationContext, pf: ProducerFactory<Any, Any>): RoutingKafkaTemplate {
        val configs = HashMap<String, Any>(pf.configurationProperties)
        configs[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = ByteArraySerializer::class.java
        val bytesPf = DefaultKafkaProducerFactory<Any, Any>(configs)
        context.registerBean(DefaultKafkaProducerFactory::class.java, "bytesPF", bytesPf)

        val map = mutableMapOf<Pattern, ProducerFactory<Any, Any>>()
        map[Pattern.compile("two")] = bytesPf
        map[Pattern.compile(".+")] = pf

        return RoutingKafkaTemplate(map)
    }



}