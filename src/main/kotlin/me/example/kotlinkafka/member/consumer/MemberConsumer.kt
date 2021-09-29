package me.example.kotlinkafka.member.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

/**
 * Created by LYT to 2021/08/17
 */
@Component
@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "default")
class MemberConsumer: AcknowledgingMessageListener<String, String> {

    private val log = LogManager.getLogger()

    @KafkaListener(topics = ["insert_member"], groupId = "test", containerFactory = "saveMemberKafkaListener")
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        try {
            log.info("Save Consumer Message:: [ $data ] value:: [ ${data.value()} ]")
//            Thread.sleep(50000)
            // ack 처리
            acknowledgment?.acknowledge()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}