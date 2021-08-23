//package me.example.kotlinkafka.member.controller
//
//import me.example.kotlinkafka.member.domain.dto.Member
//import me.example.kotlinkafka.member.producer.MemberProducer
//import me.example.kotlinkafka.member.producer.MemberSingleProducer
//import org.springframework.web.bind.annotation.*
//
///**
// * Created by LYT to 2021/08/17
// */
//@RestController
//@RequestMapping("/api/member/single")
//class MemberSingleController(
//    val producer: MemberSingleProducer
//) {
//
//    @PostMapping
//    fun saveMemberAsync(
//        @RequestBody member: Member
//    ) = producer.sendMessage(member.name)
//
//}