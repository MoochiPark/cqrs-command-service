package me.daewon.thesis.cqrscommandservice.application.port.`in`

import java.time.LocalDateTime

sealed interface SensingDataCommand {
    data class Register(
        val data: String,
        val sensingTime: LocalDateTime,
    ) : SensingDataCommand
}
