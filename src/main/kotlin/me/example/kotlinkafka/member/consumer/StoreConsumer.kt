package me.example.kotlinkafka.member.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.logging.log4j.LogManager
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

/**
 * Created by LYT to 2021/08/18
 */
@Component
class StoreConsumer: AcknowledgingMessageListener<String, String> {

    private val log = LogManager.getLogger()

    @KafkaListener(topics = ["one", "two"], groupId = "test", containerFactory = "saveMemberKafkaListener")
    override fun onMessage(data: ConsumerRecord<String, String>, acknowledgment: Acknowledgment?) {
        try {
            log.info("Member Save Consumer Message:: [ $data ] value:: [ ${data.value()} ]")

            // ack 처리
            acknowledgment?.acknowledge()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
