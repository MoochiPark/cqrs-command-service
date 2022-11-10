package me.daewon.thesis.cqrscommandservice.adapter.out.messaging.rabbitmq

import java.nio.charset.StandardCharsets
import javax.annotation.PreDestroy
import me.daewon.thesis.cqrscommandservice.application.port.out.SensingDataMessage
import me.daewon.thesis.cqrscommandservice.application.port.out.RegisterMessagePort
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toMono
import reactor.rabbitmq.OutboundMessage
import reactor.rabbitmq.QueueSpecification
import reactor.rabbitmq.Sender

private const val QUEUE: String = "cqrs-rabbitmq-queue"
@Component
class SensingDataMessageProducer(
    private val sender: Sender,
) : RegisterMessagePort {

    override suspend fun sendRegister(message: SensingDataMessage.Register) {
        val confirmation =
            sender.sendWithPublishConfirms(
                OutboundMessage("", QUEUE, message.toByteArray()).toMono(),
            )
        sender.declareQueue(QueueSpecification.queue(QUEUE))
            .thenMany(confirmation)
            .doOnError { e -> error("Send failed $e") }
            .subscribe { result ->
                if (result.isAck) {
                    println(
                        "Message ${
                            String(
                                result.outboundMessage.body,
                                StandardCharsets.UTF_8
                            )
                        } sent successfully"
                    )
                }
            }
    }

    @PreDestroy
    fun close() = sender.close()
}
