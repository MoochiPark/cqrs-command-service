package me.daewon.thesis.cqrscommandservice.domain

import java.time.LocalDateTime

data class SensingData(
    val id: String? = null,
    val data: String,
    val sensingTime: LocalDateTime,
)
