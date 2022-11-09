package me.daewon.thesis.cqrscommandservice.config

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.springframework.boot.autoconfigure.amqp.RabbitProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import reactor.rabbitmq.RabbitFlux
import reactor.rabbitmq.Sender
import reactor.rabbitmq.SenderOptions

@Configuration
class RabbitConfig {
    @Bean
    fun connectionMono(rabbitProperties: RabbitProperties): Mono<Connection> = Mono.fromCallable {
        ConnectionFactory()
            .apply {
                host = rabbitProperties.host
                port = rabbitProperties.port
                username = rabbitProperties.username
                password = rabbitProperties.password
            }
            .newConnection("cqrs-rabbitmq")
    }.cache()

    @Bean
    fun sender(connectionMono: Mono<Connection>): Sender =
        RabbitFlux.createSender(SenderOptions().connectionMono(connectionMono))
}
