//package me.example.kotlinkafka.member.controller
//
//import me.example.kotlinkafka.member.domain.dto.Member
//import me.example.kotlinkafka.member.producer.MemberProducer
//import org.springframework.web.bind.annotation.*
//
///**
// * Created by LYT to 2021/08/17
// */
//@RestController
//@RequestMapping("/api/member")
//class MemberController(
//    val producer: MemberProducer
//) {
//
//    @PostMapping("/async")
//    fun saveMemberAsync(
//        @RequestBody member: Member
//    ) = producer.sendMessage(member.name)
//
//    @PostMapping("sync")
//    fun saveMemberSync(
//        @RequestBody member: Member
//    ) = producer.sendMessage(member.name)
//}