package me.example.kotlinkafka.member.consumer.listener

import me.example.kotlinkafka.member.domain.dto.Member
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.KafkaListenerErrorHandler
import org.springframework.kafka.listener.ListenerExecutionFailedException
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import javax.validation.Valid

/**
 * Created by LYT to 2021/09/30
 */
@Component
class MemberValidationTestConsumer {

    @KafkaListener(
        id = "member_validated",
        topics = ["insert_member"],
        containerFactory = "memberFactory",
        groupId = "m_group",
        errorHandler = "validationErrorHandler"
    )
    fun memberListener(@Payload @Valid member: Member, acknowledgment: Acknowledgment) {
        println("memberListener data:: $member")
        acknowledgment.acknowledge()
    }

    @Bean
    fun validationErrorHandler(): KafkaListenerErrorHandler {
        return KafkaListenerErrorHandler { message, exception ->
            println("Consumer Exception!! Message=$message")
            exception.printStackTrace()
        }
    }
}