package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.MongoService
import com.mitchmele.seeker.model.Trade
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.integration.handler.GenericHandler
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class TradeMessageHandler(
        private val mongoService: MongoService
) : GenericHandler<Trade> {

    val logger: Logger = LoggerFactory.getLogger(TradeMessageHandler::class.java)

    override fun handle(payload: Trade?, headers: MessageHeaders?): Message<Trade>? {
        return payload?.let { trade ->
                    mongoService.saveTrade(trade)
                            .also { logger.info("TRADE: {} HAS BEEN SUCCESSFULLY SAVED TO MONGO", trade) }
                    MessageBuilder.withPayload(payload).copyHeadersIfAbsent(headers).build()
                }
    }
}
