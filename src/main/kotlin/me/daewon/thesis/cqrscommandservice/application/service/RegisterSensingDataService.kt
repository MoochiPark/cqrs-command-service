package me.daewon.thesis.cqrscommandservice.application.service

import me.daewon.thesis.cqrscommandservice.application.port.`in`.RegisterSensingDataUseCase
import me.daewon.thesis.cqrscommandservice.application.port.`in`.SensingDataCommand
import me.daewon.thesis.cqrscommandservice.application.port.`in`.SensingDataResponse
import me.daewon.thesis.cqrscommandservice.application.port.out.SaveSensingDataPort
import me.daewon.thesis.cqrscommandservice.application.port.out.RegisterMessagePort
import me.daewon.thesis.cqrscommandservice.application.port.out.SensingDataMessage
import me.daewon.thesis.cqrscommandservice.domain.SensingData
import org.springframework.stereotype.Service

@Service
class RegisterSensingDataService(
    private val saveSensingDataPort: SaveSensingDataPort,
    private val sendMessagePort: RegisterMessagePort,
) : RegisterSensingDataUseCase {
    override suspend fun register(
        command: SensingDataCommand.Register,
    ): SensingDataResponse.Register =
        saveSensingDataPort.save(command.toDomain())
            .let {
                sendMessagePort.sendRegister(
                    SensingDataMessage.Register.from(it)
                )
                SensingDataResponse.Register.from(it)
            }
}
