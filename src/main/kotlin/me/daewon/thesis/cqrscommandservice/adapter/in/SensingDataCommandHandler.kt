package me.daewon.thesis.cqrscommandservice.adapter.`in`

import java.net.URI
import me.daewon.thesis.cqrscommandservice.application.port.`in`.RegisterSensingDataUseCase
import me.daewon.thesis.cqrscommandservice.application.port.`in`.SensingDataCommand
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class SensingDataCommandHandler(
    private val registerUseCase: RegisterSensingDataUseCase,
) {
    suspend fun register(request: ServerRequest): ServerResponse =
        request.awaitBody<SensingDataCommand.Register>().let {
            registerUseCase.register(it).let {
                created(URI.create("/sensing-data/${it.id}"))
                    .buildAndAwait()
            }
        }
}
