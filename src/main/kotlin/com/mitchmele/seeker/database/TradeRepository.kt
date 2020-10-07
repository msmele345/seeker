package com.mitchmele.seeker.database

import com.mitchmele.seeker.model.Trade
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TradeRepository : MongoRepository<Trade, Int>
