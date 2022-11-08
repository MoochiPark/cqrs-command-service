package me.daewon.thesis.cqrscommandservice.adapter.out.persistence.mongodb

import me.daewon.thesis.cqrscommandservice.adapter.out.persistence.mongodb.document.SensingDataDocument
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SensingDataMongoRepository : CoroutineCrudRepository<SensingDataDocument, String>
