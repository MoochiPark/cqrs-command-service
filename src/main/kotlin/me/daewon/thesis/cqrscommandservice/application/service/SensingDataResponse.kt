package me.daewon.thesis.cqrscommandservice.application.service

import java.time.LocalDateTime

sealed interface SensingDataResponse {
    data class Register(
        val id: String?,
        val data: String,
        val sensingTime: LocalDateTime,
    ) : SensingDataResponse
}
