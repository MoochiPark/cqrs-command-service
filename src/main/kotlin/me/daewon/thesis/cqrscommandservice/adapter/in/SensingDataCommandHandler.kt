package me.daewon.thesis.cqrscommandservice.adapter.`in`

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.RedisFuture
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import io.lettuce.core.api.coroutines.multi
import java.net.URI
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import kotlin.system.measureNanoTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.daewon.thesis.cqrscommandservice.application.port.`in`.RegisterSensingDataUseCase
import me.daewon.thesis.cqrscommandservice.application.port.`in`.SensingDataCommand
import me.daewon.thesis.cqrscommandservice.config.mapper
import me.daewon.thesis.cqrscommandservice.domain.SensingData
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
@ExperimentalLettuceCoroutinesApi
class SensingDataCommandHandler(
    private val registerUseCase: RegisterSensingDataUseCase,
    private val redisApi: RedisAsyncCommands<String, String>,
) {
    private var count: Long = 0
    suspend fun register(request: ServerRequest): ServerResponse =
        request.awaitBody<SensingDataCommand.Register>().let {
            registerUseCase.register(it).let {
                created(URI.create("/sensing-data/${it.id}"))
                    .buildAndAwait()
            }
        }

    suspend fun testCommand(request: ServerRequest): ServerResponse {
        val it = request.awaitBody<SensingDataCommand.Register>()
        CoroutineScope(Dispatchers.Default).launch {
                redisApi.rpush(
                    it.serialNumber,
                    mapper.writeValueAsString(SensingDataValue.from(it.toDomain()))
                )
            count.inc()
            if (count.rem(5) == 0L) {
                count = 0
                redisApi.flushCommands()
            }
        }
        return ok().buildAndAwait()
    }

    data class SensingDataValue(
        val data: String,
        val sensingTime: LocalDateTime,
    ) {
        companion object {
            fun from(sensingData: SensingData) =
                SensingDataValue(
                    data = sensingData.data,
                    sensingTime = sensingData.sensingTime
                )
        }
    }
}
