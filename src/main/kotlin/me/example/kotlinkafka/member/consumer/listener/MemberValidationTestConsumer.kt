package me.example.kotlinkafka.member.consumer.listener

import me.example.kotlinkafka.member.domain.dto.Member
import org.apache.kafka.clients.consumer.Consumer
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.KafkaListenerErrorHandler
import org.springframework.kafka.listener.ListenerExecutionFailedException
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException
import org.springframework.stereotype.Component
import javax.validation.Valid

/**
 * Created by LYT to 2021/09/30
 */
@Component
class MemberValidationTestConsumer {

//    @KafkaListener(
//        id = "member_reply",
//        topics = ["insert_member"],
//        containerFactory = "memberFactory",
//        groupId = "m_group",
//        errorHandler = "validationErrorHandler"
//    )
//    @SendTo(value = ["reply_insert_member"])
//    fun memberListener1(@Payload @Valid member: Member, meta: ConsumerRecordMetadata, acknowledgment: Acknowledgment) {
//        println("memberListener data:: $member")
//        println("memberListener offset:${meta.offset()} partition:${meta.partition()}")
//        if (member.age == 10)
//            throw RuntimeException("그냥 실패해라!!!!")
//
//        acknowledgment.acknowledge()
//    }

    @KafkaListener(
        id = "member_retry",
        topics = ["insert_member"],
        containerFactory = "memberFactory",
        groupId = "m_group",
        errorHandler = "validationErrorHandler"
    )
//    @SendTo(value = ["reply_insert_member"])
    fun memberListener2(@Payload @Valid member: Member, meta: ConsumerRecordMetadata, acknowledgment: Acknowledgment) {
        println("memberListener data:: $member")
        println("memberListener offset:${meta.offset()} partition:${meta.partition()}")
//        if (member.age == 10)
//            throw RuntimeException("그냥 실패해라!!!!")
//
        acknowledgment.acknowledge()
    }

    @Bean
    fun validationErrorHandler(): KafkaListenerErrorHandler {
        return object : KafkaListenerErrorHandler {
            override fun handleError(msg: Message<*>, exception: ListenerExecutionFailedException): Any {
                return msg
            }

            override fun handleError(
                msg: Message<*>,
                exception: ListenerExecutionFailedException,
                consumer: Consumer<*, *>
            ): Any {
                val member = msg.payload as Member
                when {
                    exception.contains(MethodArgumentNotValidException::class.java) -> {
                        saveFailData(member, exception)
                        consumer.commitAsync()
                    }
                    exception.contains(RuntimeException::class.java) -> throw RuntimeException("error Handler!!!!", exception)
                }

                return super.handleError(msg, exception, consumer)
            }
        }
    }

    private fun saveFailData(member: Member, e: Exception) {
        // save
        println("failed process data:: $member")
        println("exception: $e")
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
}