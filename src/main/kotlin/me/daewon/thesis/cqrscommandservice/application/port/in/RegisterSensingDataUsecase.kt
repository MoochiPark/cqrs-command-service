package me.daewon.thesis.cqrscommandservice.application.port.`in`

import me.daewon.thesis.cqrscommandservice.application.service.SensingDataResponse

interface RegisterSensingDataUseCase {
    suspend fun register(
        command: SensingDataCommand.Register,
    ): SensingDataResponse.Register
}
