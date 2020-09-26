package com.mitchmele.seeker.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories
class MongoConfiguration : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String {
        return TRADE_REPOSITORY
    }

    override fun mongoClient() : MongoClient {
        return MongoClients.create()
    }

    companion object {
        const val TRADE_REPOSITORY = "tradeRepo"
    }
}