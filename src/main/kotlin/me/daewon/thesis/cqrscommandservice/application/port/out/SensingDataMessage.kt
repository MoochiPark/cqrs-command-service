package me.daewon.thesis.cqrscommandservice.application.port.out

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import java.time.LocalDateTime
import me.daewon.thesis.cqrscommandservice.domain.SensingData

private val mapper: ObjectMapper = jsonMapper {
    addModule(kotlinModule())
    addModule(JavaTimeModule())
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}

sealed interface SensingDataMessage {
    data class Register(
        val id: String,
        val serialNumber: String,
        val data: String,
        val sensingTime: LocalDateTime,
    ) {
        companion object {
            fun from(sensingData: SensingData): Register =
                Register(
                    sensingData.id
                        ?: throw IllegalArgumentException("id must be not null"),
                    sensingData.serialNumber,
                    sensingData.data,
                    sensingData.sensingTime
                )
        }

        fun toJsonByteArray(): ByteArray =
            mapper.writeValueAsBytes(this)
    }

}
