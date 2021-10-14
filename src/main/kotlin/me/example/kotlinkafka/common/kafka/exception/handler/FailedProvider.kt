package me.example.kotlinkafka.common.kafka.exception.handler

import org.springframework.kafka.support.serializer.FailedDeserializationInfo
import java.util.function.Function

/**
 * Created by LYT to 2021/10/13
 */
class FailedProvider: Function<FailedDeserializationInfo, BadMember> {
    override fun apply(t: FailedDeserializationInfo): BadMember {
        return BadMember(t)
    }
}