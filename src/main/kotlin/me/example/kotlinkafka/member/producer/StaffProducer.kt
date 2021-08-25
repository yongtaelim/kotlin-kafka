package me.example.kotlinkafka.member.producer

import me.example.kotlinkafka.common.kafka.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * Created by LYT to 2021/08/18
 */
@Component
@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "replying")
class StaffProducer(
    val template: ReplyingKafkaTemplate<String, String, String>
): KafkaProducer() {

    override fun sendMessage(message: String) {
        template.setReplyErrorChecker {
            val error = it.headers().lastHeader("serverSentAnError")
            error?.let {
                println(error.value())
                throw RuntimeException("Exception!! cause=${error.value()}")
            }
        }

        val topic = "kRequests1"
        val record = ProducerRecord<String, String>(topic, message)
//        record.headers().add(KafkaHeaders.REPLY_TOPIC, "replyingTestTopic".toByteArray())
        val replyFuture = template.sendAndReceive(record)

        val sendResult = replyFuture.sendFuture.get(10, TimeUnit.SECONDS)
        println("Sent OK=${sendResult.recordMetadata} ProductRecode=${sendResult.producerRecord}")

        val consumerRecord = replyFuture.get(10, TimeUnit.SECONDS)
        println("Return value=${consumerRecord.value()}")   // 이게 잘 안되네....
    }

}