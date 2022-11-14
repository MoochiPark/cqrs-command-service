package me.daewon.thesis.cqrscommandservice.application.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.daewon.thesis.cqrscommandservice.application.port.`in`.RegisterSensingDataUseCase
import me.daewon.thesis.cqrscommandservice.application.port.`in`.SensingDataCommand
import me.daewon.thesis.cqrscommandservice.application.port.`in`.SensingDataResponse
import me.daewon.thesis.cqrscommandservice.application.port.out.RegisterMessagePort
import me.daewon.thesis.cqrscommandservice.application.port.out.SaveSensingDataPort
import me.daewon.thesis.cqrscommandservice.application.port.out.SensingDataMessage
import org.springframework.stereotype.Service

@Service
class RegisterSensingDataService(
    private val saveSensingDataPort: SaveSensingDataPort,
    private val sendMessagePort: RegisterMessagePort,
) : RegisterSensingDataUseCase {
    override suspend fun register(
        command: SensingDataCommand.Register,
    ): SensingDataResponse.Register =
        command.toDomain().let {
            saveSensingDataPort.save(it).let { result ->
                CoroutineScope(Dispatchers.IO).launch {
                    println(result)
                    sendMessagePort.sendRegister(
                        SensingDataMessage.Register.from(result)
                    )
                }
                SensingDataResponse.Register.from(result)
            }
        }

}
