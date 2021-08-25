package me.example.kotlinkafka.member.controller

import me.example.kotlinkafka.member.domain.dto.Store
import me.example.kotlinkafka.member.producer.StoreProducer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by LYT to 2021/08/18
 */
@ConditionalOnProperty(value = ["spring.kafka.type"], havingValue = "routing")
@RestController
@RequestMapping("/api/store")
class StoreController(
    val producer: StoreProducer
) {

    @PostMapping
    fun saveStore(
        @RequestBody store: Store
    ) = producer.sendMessage(store.name)

}