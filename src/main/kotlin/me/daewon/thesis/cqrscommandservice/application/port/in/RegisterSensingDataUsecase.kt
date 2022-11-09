package me.daewon.thesis.cqrscommandservice.application.port.`in`

interface RegisterSensingDataUseCase {
    suspend fun register(
        command: SensingDataCommand.Register,
    ): SensingDataResponse.Register
}
