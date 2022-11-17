package me.daewon.thesis.cqrscommandservice.adapter.`in`

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Configuration
@ExperimentalLettuceCoroutinesApi
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
                    "/test".nest {
                        POST("", sensingDataCommandHandler::testCommand)
                    }

                }
            }
        }
}
