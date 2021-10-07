package me.example.kotlinkafka.common.kafka.consumer.config

import me.example.kotlinkafka.member.domain.dto.Member
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListenerConfigurer
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import java.io.Serializable

/**
 * Created by LYT to 2021/09/30
 *
 * spring-kafka에서 spring-validation 사용 configuration
 */
@Configuration
@EnableKafka
class KafkaValidatorConfig(
    private val validator: LocalValidatorFactoryBean
) : KafkaListenerConfigurer {

    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    private lateinit var BOOTSTRAP_SERVER: String

    override fun configureKafkaListeners(registrar: KafkaListenerEndpointRegistrar) {
        registrar.setValidator(this.validator)
    }

    @Bean
    fun memberFactory(): ConcurrentKafkaListenerContainerFactory<String, Member> {
        return ConcurrentKafkaListenerContainerFactory<String, Member>().also {
            it.consumerFactory =
                DefaultKafkaConsumerFactory(getConfig(), StringDeserializer(), JsonDeserializer(Member::class.java))
            it.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
            it.setReplyTemplate(replyTemplate())

            it.containerProperties.setConsumerRebalanceListener(object : ConsumerAwareRebalanceListener {

            })
        }
    }

    @Bean
    fun replyMemberFactory(): ConcurrentKafkaListenerContainerFactory<String, Member> {
        return ConcurrentKafkaListenerContainerFactory<String, Member>().also {
            it.consumerFactory =
                DefaultKafkaConsumerFactory(getConfig(), StringDeserializer(), JsonDeserializer(Member::class.java))
            it.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        }
    }

    private fun getConfig(): Map<String, Any> =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",    // 마지막 읽은 부분부터 Read
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
        )

    /**
     * reply kafka template
     * @return KafkaTemplate<String, Member>
     */
    fun replyTemplate(): KafkaTemplate<String, Member> {
        return KafkaTemplate(factory())
    }

    /**
     * reply kafka template factory
     * @return DefaultKafkaProducerFactory<String, Member>
     */
    fun factory(): DefaultKafkaProducerFactory<String, Member> {
        return DefaultKafkaProducerFactory<String, Member>(memberProducerConfig())
    }

    private fun memberProducerConfig(): Map<String, Serializable> =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )

}