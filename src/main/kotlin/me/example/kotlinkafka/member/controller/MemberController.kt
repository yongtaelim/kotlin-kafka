package me.example.kotlinkafka.member.controller

import me.example.kotlinkafka.member.domain.dto.Member
import me.example.kotlinkafka.member.producer.MemberProducer
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.web.bind.annotation.*

/**
 * Created by LYT to 2021/08/17
 */
@RestController
@RequestMapping("/api/member")
class MemberController(
    val producer: MemberProducer,
    val registry: KafkaListenerEndpointRegistry
) {

    @PostMapping("/async")
    fun saveMemberAsync(
        @RequestBody member: Member
    ) = producer.sendMessage(member.name!!)

    @PatchMapping("/start")
    fun consumerStart() {
        val listener1 = registry.getListenerContainer("listener1")
//        val b = registry.allListenerContainers
//        for (messageListenerContainer in b) {
//            println("""
//                    listenerId:: ${messageListenerContainer.listenerId}
//                    assignedPartitions:: ${messageListenerContainer.assignedPartitions}
//                    assignmentsByClientId:: ${messageListenerContainer.assignmentsByClientId}
//                    groupId:: ${messageListenerContainer.groupId}
//            """.trimIndent())
//            messageListenerContainer.start()
//        }
        listener1?.start()
    }

    @PatchMapping("/stop")
    fun consumerStop() {
        val listener1 = registry.getListenerContainer("listener1")
//        val b = registry.allListenerContainers
//        for (messageListenerContainer in b) {
//            println("""
//                    listenerId:: ${messageListenerContainer.listenerId}
//                    assignedPartitions:: ${messageListenerContainer.assignedPartitions}
//                    assignmentsByClientId:: ${messageListenerContainer.assignmentsByClientId}
//                    groupId:: ${messageListenerContainer.groupId}
//            """.trimIndent())
//            messageListenerContainer.start()
//        }
        listener1?.stop()
    }


    @PostMapping("sync")
    fun saveMemberSync(
        @RequestBody member: Member
    ) = producer.sendMessage(member.name!!)

    @PostMapping("/string")
    fun saveMemberString(
        @RequestBody member: Member
    ) = producer.sendMessageString(member.name!!)

    @PostMapping("/int")
    fun saveMemberInt(
        @RequestBody member: Member
    ) = producer.sendMessageInt(member.age!!)
}