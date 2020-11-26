package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.MongoService
import com.mitchmele.seeker.model.Trade
import com.mitchmele.seeker.model.TradeEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.integration.handler.GenericHandler
import org.springframework.messaging.MessageHeaders
import org.springframework.stereotype.Service

@Service
class TradeMessageHandler(
        private val mongoService: MongoService,
        private val tradeEntityService: TradeEntityService
) : GenericHandler<Trade> {

    val logger: Logger = LoggerFactory.getLogger(TradeMessageHandler::class.java)

    override fun handle(payload: Trade?, headers: MessageHeaders?)  {

         payload?.let { trade ->
            mongoService.saveTrade(trade)
            tradeEntityService.save(
                    TradeEntity(
                        id = trade.id,
                            askId = trade.askId,
                            bidId = trade.bidId,
                            symbol = trade.symbol,
                            tradePrice = trade.tradePrice,
                            timeStamp = trade.timeStamp
                    )).also { logger.info("TRADE: {} HAS BEEN SUCCESSFULLY SAVED TO MONGO AND MYSQL", trade) }
        }
    }
}
