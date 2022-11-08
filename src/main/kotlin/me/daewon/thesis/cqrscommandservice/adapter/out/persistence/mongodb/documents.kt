package me.daewon.thesis.cqrscommandservice.adapter.out.persistence.mongodb.document

import java.time.LocalDateTime
import me.daewon.thesis.cqrscommandservice.domain.SensingData
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sensing_data")
data class SensingDataDocument(
    @Id
    val id: String? = null,
    val data: String,
    val sensingTime: LocalDateTime,
) {
    companion object {
        fun from(sensingData: SensingData): SensingDataDocument =
            SensingDataDocument(
                id = sensingData.id,
                data = sensingData.data,
                sensingTime = sensingData.sensingTime,
            )
    }

    fun toDomain(): SensingData =
        SensingData(id, data, sensingTime)
}
