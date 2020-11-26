package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.TradeEntityRepository
import com.mitchmele.seeker.model.TradeEntity
import org.springframework.stereotype.Service

@Service
class TradeEntityService(
        private val tradeEntityRepository: TradeEntityRepository
) {

    fun save(trade: TradeEntity) {
        tradeEntityRepository.save(trade)
    }
}