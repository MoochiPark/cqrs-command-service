package me.daewon.thesis.cqrscommandservice.adapter.out.persistence.mongodb

import me.daewon.thesis.cqrscommandservice.adapter.out.persistence.mongodb.document.SensingDataDocument
import me.daewon.thesis.cqrscommandservice.application.port.out.SaveSensingDataPort
import me.daewon.thesis.cqrscommandservice.domain.SensingData
import org.springframework.stereotype.Component

@Component
class SensingDataMongoAdapter(
    private val sensingDataRepository: SensingDataMongoRepository,
) : SaveSensingDataPort {
    override suspend fun save(sensingData: SensingData): SensingData =
        sensingDataRepository
            .save(SensingDataDocument.from(sensingData))
            .toDomain()
}
