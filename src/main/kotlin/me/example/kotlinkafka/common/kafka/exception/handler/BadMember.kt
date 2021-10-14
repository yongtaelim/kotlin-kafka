package me.example.kotlinkafka.common.kafka.exception.handler

import me.example.kotlinkafka.member.domain.dto.Member
import org.springframework.kafka.support.serializer.FailedDeserializationInfo
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Created by LYT to 2021/10/13
 */
class BadMember(
    private val failedDeserializationInfo: FailedDeserializationInfo
) {
}