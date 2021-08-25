package me.example.kotlinkafka.common.kafka.producer

import org.apache.logging.log4j.LogManager
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaSendCallback
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFutureCallback

/**
 * Created by LYT to 2021/08/17
 */
abstract class KafkaProducer {

    private val log = LogManager.getLogger()

    /** kafka send message */
    abstract fun sendMessage(message: String)

    /** kafka callback listener */
    fun listenableFutureCallback(message: String) =
    //        object: ListenableFutureCallback<SendResult<String, String>> {  // 2.5 이전
        object: KafkaSendCallback<String, String> {  // 2.5 이후
            override fun onSuccess(result: SendResult<String, String>?) {
                println(
                    """
                        Send Message = [ $message ] 
                          - Offset = [ ${result!!.recordMetadata.offset()} ], Topic = [ ${result.recordMetadata.topic()} ], Partition = [ ${result.recordMetadata.partition()} ]
                    """.trimIndent()
                )
            }

    //            2.5 이전
    //            override fun onFailure(ex: Throwable) {
    //                log.error(
    //                    "Message 전달 오류 [ $message ] due to: ${ex.message}", ex
    //                )
    //            }

            // 2.5 이후
            override fun onFailure(ex: KafkaProducerException) {
                println(
                    "Message 전달 오류 [ $message ] due to: ${ex.getFailedProducerRecord<String, String>()}"
                )
            }

        }
}