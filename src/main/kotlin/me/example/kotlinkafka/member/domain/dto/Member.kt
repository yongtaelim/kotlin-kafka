package me.example.kotlinkafka.member.domain.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * Created by LYT to 2021/08/17
 */
data class Member(

   @field:NotNull
   val name: String? = null,

   @field:Min(1)
   @field:Max(20)
   val age: Int? = null
)