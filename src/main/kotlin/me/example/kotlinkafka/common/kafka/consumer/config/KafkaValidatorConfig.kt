package me.example.kotlinkafka.common.kafka.consumer.config

import me.example.kotlinkafka.member.domain.dto.Member
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListenerConfigurer
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

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
        val cf = DefaultKafkaConsumerFactory(getConfig(), StringDeserializer(), JsonDeserializer(Member::class.java))
        return ConcurrentKafkaListenerContainerFactory<String, Member>().also {
            it.consumerFactory = cf
            it.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        }
    }

    private fun getConfig(): Map<String, Any> =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest",    // 마지막 읽은 부분부터 Read
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to false,
        )
}