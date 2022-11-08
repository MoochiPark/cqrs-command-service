package me.daewon.thesis.cqrscommandservice.adapter.`in`

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CommandServiceRouter(
    private val sensingDataCommandHandler: SensingDataCommandHandler,
) {
    @Bean
    fun commandRoutes() =
        coRouter {
            "/api".nest {
                accept(APPLICATION_JSON).nest {
                    "/sensing-data".nest {
                        POST("", sensingDataCommandHandler::register)
                    }
                }
            }
        }
}
