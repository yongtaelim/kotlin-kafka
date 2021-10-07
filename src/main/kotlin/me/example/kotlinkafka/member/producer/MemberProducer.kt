package me.example.kotlinkafka.member.producer

import me.example.kotlinkafka.common.kafka.producer.KafkaProducer
import me.example.kotlinkafka.member.domain.dto.Member
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by LYT to 2021/08/17
 */
@Component
class MemberProducer(
    val kafkaTemplate: KafkaTemplate<String, String>,
    val kafkaTemplate1: KafkaTemplate<String, Int>,
    val memberKafkaTemplate: KafkaTemplate<String, Member>
): KafkaProducer() {
    companion object {
        const val TOPIC_NAME = "insert_member"
    }

    /**
     * async send message
     * @param message String
     */
    override fun sendMessage(message: String) {
        // kafka producer
        val listenableFuture = kafkaTemplate.send(TOPIC_NAME, message)

        // kafka add callback
        listenableFuture.addCallback(listenableFutureCallback(message))
    }

    fun sendMessageString(message: String) {
        // kafka producer
        kafkaTemplate.send(TOPIC_NAME, message)
    }

    fun sendMessageInt(message: Int) {
        // kafka producer
        kafkaTemplate1.send(TOPIC_NAME, message)
    }

    fun sendMessageObject(member: Member) {
        memberKafkaTemplate.send(TOPIC_NAME, member.name!!, member)
    }

    /**
     * sync send message
     * @param message String
     */
    fun sendMessageSync(message: String) {
        // kafka producer
        val listenableFuture = kafkaTemplate.send(TOPIC_NAME, message)

        try {
            listenableFuture.get(10, TimeUnit.SECONDS)
            // success 처리
        } catch (e: ExecutionException) {
            // failure 처리
        } catch (e: TimeoutException) {
            // failure 처리
        } catch (e: InterruptedException) {
            // failure 처리
        }
    }
}