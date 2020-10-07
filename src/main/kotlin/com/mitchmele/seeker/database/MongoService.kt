package com.mitchmele.seeker.database

import com.mitchmele.seeker.model.Trade
import org.springframework.stereotype.Service

@Service
class MongoService(
        private val tradeRepository: TradeRepository
) {

    fun saveTrade(trade: Trade): Trade? = tradeRepository.save(trade)
}