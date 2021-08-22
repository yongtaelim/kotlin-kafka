package me.example.kotlinkafka.member.controller

import me.example.kotlinkafka.member.domain.dto.Staff
import me.example.kotlinkafka.member.producer.StaffProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by LYT to 2021/08/18
 */
@RestController
@RequestMapping("/api/staff")
class StaffController(
    val producer: StaffProducer
) {

    @PostMapping
    fun saveStaff(
        @RequestBody staff: Staff
    ) = producer.sendMessage(staff.name)

}