package me.example.kotlinkafka.member.consumer.listener

import me.example.kotlinkafka.member.domain.dto.Member
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.KafkaListenerErrorHandler
import org.springframework.kafka.listener.ListenerExecutionFailedException
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
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
    @SendTo(value = ["reply_insert_member"])
    fun memberListener(@Payload @Valid member: Member, meta: ConsumerRecordMetadata, acknowledgment: Acknowledgment) {
        println("memberListener data:: $member")
        println("memberListener offset:${meta.offset()} partition:${meta.partition()}")
        acknowledgment.acknowledge()
    }

    @KafkaListener(
        id = "reply_member_validated",
        topics = ["reply_insert_member"],
        containerFactory = "replyMemberFactory",
        groupId = "m_group"
    )
    fun replyMemberListener(member: Member, meta: ConsumerRecordMetadata, acknowledgment: Acknowledgment) {
        println("replyMemberListener data:: $member")
        println("replyMemberListener offset:${meta.offset()} partition:${meta.partition()}")
        acknowledgment.acknowledge()
    }

    @Bean
    fun validationErrorHandler(): KafkaListenerErrorHandler {
        return KafkaListenerErrorHandler { msg, e ->
            var member = msg.payload as Member
            println("error handler payload:: $member")
            println("exception: $e")
            member
        }
    }
}