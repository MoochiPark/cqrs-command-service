package me.daewon.thesis.cqrscommandservice.application.port.out

import java.time.LocalDateTime
import me.daewon.thesis.cqrscommandservice.domain.SensingData

sealed interface SensingDataMessage {
    data class Register(
        private val id: String?,
        private val serialNumber: String,
        private val data: String,
        private val sensingTime: LocalDateTime,
    ) {
        companion object {
            fun from(sensingData: SensingData): Register =
                Register(
                    sensingData.id,
                    sensingData.serialNumber,
                    sensingData.data,
                    sensingData.sensingTime
                )
        }

        fun toByteArray(): ByteArray =
            this.toString().toByteArray()
    }

}
