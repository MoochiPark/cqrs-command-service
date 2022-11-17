package me.daewon.thesis.cqrscommandservice.config

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.api.async.RedisAsyncCommands
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig(
    @Value("\${spring.redis.port}")
    private val port: Int,
    @Value("\${spring.redis.host}")
    private val host: String,
) {
    @Bean
    @ExperimentalLettuceCoroutinesApi
    fun redisApi(): RedisAsyncCommands<String, String>? =
        RedisClient.create("redis://$host:$port/")
            .connect()
            .async().apply {
                setAutoFlushCommands(false)
            }

}
