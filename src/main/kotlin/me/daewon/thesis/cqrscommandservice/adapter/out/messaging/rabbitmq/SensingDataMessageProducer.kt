package me.daewon.thesis.cqrscommandservice.adapter.out.messaging.rabbitmq

import javax.annotation.PreDestroy
import kotlinx.coroutines.reactor.awaitSingleOrNull
import me.daewon.thesis.cqrscommandservice.application.port.out.SensingDataMessage
import me.daewon.thesis.cqrscommandservice.application.port.out.RegisterMessagePort
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toMono
import reactor.rabbitmq.OutboundMessage
import reactor.rabbitmq.Sender

private const val QUEUE: String = "cqrs-rabbitmq-queue"

@Component
class SensingDataMessageProducer(
    private val sender: Sender,
) : RegisterMessagePort {

    override suspend fun sendRegister(message: SensingDataMessage.Register): Unit {
        sender.send(
            OutboundMessage("", QUEUE, message.toJsonByteArray()).toMono(),
        ).awaitSingleOrNull()
    }

    @PreDestroy
    fun close() = sender.close()
}
