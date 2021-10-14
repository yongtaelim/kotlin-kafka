package me.example.kotlinkafka.common.kafka.consumer.config

import me.example.kotlinkafka.member.domain.dto.Member
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
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
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.remoting.RemoteAccessException
import org.springframework.retry.policy.TimeoutRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.util.backoff.FixedBackOff
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

/**
 * Created by LYT to 2021/09/30
 *
 * spring-kafka에서 spring-validation 사용 configuration
 */
@Configuration
@EnableKafka
class KafkaValidatorConfig(
    /** spring validation */
    private val validator: LocalValidatorFactoryBean,
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

            // retry
            it.setErrorHandler(SeekToCurrentErrorHandler(FixedBackOff(0, 3L)))
            it.setRetryTemplate(retryTemplate())
            it.setStatefulRetry(true)
            it.setRecoveryCallback { context ->
                println("retry count:: ${context.retryCount}")
                println("attribute names:: ${context.attributeNames()}")
                println("consumer retry:: $context")
                val consumer = context.getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD) as ConsumerRecord<*, *>
                println("consumerRecord:: $consumer")
                null
            }

            // set reply
//            it.setReplyTemplate(replyTemplate())

            // set rebalancing listener
//            it.containerProperties.setConsumerRebalanceListener(object : ConsumerAwareRebalanceListener {
//                override fun onPartitionsRevokedBeforeCommit(
//                    consumer: Consumer<*, *>,
//                    partitions: MutableCollection<TopicPartition>
//                ) {
//                    super.onPartitionsRevokedBeforeCommit(consumer, partitions)
//                }
//
//                override fun onPartitionsRevokedAfterCommit(
//                    consumer: Consumer<*, *>,
//                    partitions: MutableCollection<TopicPartition>
//                ) {
//                    super.onPartitionsRevokedAfterCommit(consumer, partitions)
//                }
//
//                override fun onPartitionsAssigned(partitions: MutableCollection<TopicPartition>) {
//                    super.onPartitionsAssigned(partitions)
//                }
//            })

        }
    }

    /**
     * retry template
     * @return RetryTemplate
     */
    private fun retryTemplate(): RetryTemplate {
        val retryTemplate = RetryTemplate.builder()
            .retryOn(RemoteAccessException::class.java)
            .retryOn(RuntimeException::class.java)
            .build()

        retryTemplate.setRetryPolicy(retryTemplatePolicy())
        return retryTemplate
    }

    /**
     * retry template policy
     * @return TimeoutRetryPolicy
     */
    private fun retryTemplatePolicy(): TimeoutRetryPolicy {
        val timePolicy = TimeoutRetryPolicy()
        timePolicy.timeout = 30000L
        return timePolicy
    }

    /**
     * reply member factory
     * @return ConcurrentKafkaListenerContainerFactory<String, Member>
     */
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

//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ErrorHandlingDeserializer::class,
//            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS to JsonDeserializer::class.java,
//            ErrorHandlingDeserializer.VALUE_FUNCTION to FailedProvider::class.java
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

    /**
     * reply kafka configuration
     * @return Map<String, Serializable>
     */
    private fun memberProducerConfig(): Map<String, Any> =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVER,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )

}