package me.daewon.thesis.cqrscommandservice.application.port.`in`

import java.time.LocalDateTime
import me.daewon.thesis.cqrscommandservice.domain.SensingData

sealed interface SensingDataCommand {
    data class Register(
        val data: String,
        val sensingTime: LocalDateTime,
    ) : SensingDataCommand {
        fun toDomain(): SensingData =
            SensingData(data = data, sensingTime = sensingTime)
    }
}
