package me.daewon.thesis.cqrscommandservice.application.port.`in`

import java.time.LocalDateTime
import me.daewon.thesis.cqrscommandservice.domain.SensingData

sealed interface SensingDataResponse {
    data class Register(
        val id: String?,
        val serialNumber: String,
        val data: String,
        val sensingTime: LocalDateTime,
    ) : SensingDataResponse {
        companion object {
            fun from(sensingData: SensingData): Register =
                Register(
                    sensingData.id,
                    sensingData.serialNumber,
                    sensingData.data,
                    sensingData.sensingTime
                )
        }
    }
}
