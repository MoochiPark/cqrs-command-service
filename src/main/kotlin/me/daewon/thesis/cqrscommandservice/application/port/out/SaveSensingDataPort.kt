package me.daewon.thesis.cqrscommandservice.application.port.out

import me.daewon.thesis.cqrscommandservice.domain.SensingData

interface SaveSensingDataPort {
    suspend fun save(sensingData: SensingData): SensingData
}
