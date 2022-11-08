package me.daewon.thesis.cqrscommandservice.application.service

import me.daewon.thesis.cqrscommandservice.application.port.`in`.RegisterSensingDataUseCase
import me.daewon.thesis.cqrscommandservice.application.port.`in`.SensingDataCommand
import me.daewon.thesis.cqrscommandservice.application.port.out.SaveSensingDataPort
import me.daewon.thesis.cqrscommandservice.domain.SensingData
import org.springframework.stereotype.Service

@Service
class RegisterSensingDataService(
    private val saveSensingDataPort: SaveSensingDataPort,
) : RegisterSensingDataUseCase {
    override suspend fun register(
        command: SensingDataCommand.Register,
    ): SensingDataResponse.Register =
        saveSensingDataPort.save(
            command.let {
                SensingData(
                    data = it.data,
                    sensingTime = it.sensingTime
                )
            }).let {
            SensingDataResponse.Register(
                id = it.id,
                data = it.data,
                sensingTime = it.sensingTime
            )
        }
}
