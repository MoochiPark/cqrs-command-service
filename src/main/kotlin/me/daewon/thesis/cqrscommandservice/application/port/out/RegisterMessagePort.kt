package me.daewon.thesis.cqrscommandservice.application.port.out

interface RegisterMessagePort {
    suspend fun sendRegister(message: SensingDataMessage.Register)
}
