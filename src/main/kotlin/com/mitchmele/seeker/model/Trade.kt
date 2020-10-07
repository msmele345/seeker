package com.mitchmele.seeker.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Document(collection = "tradeRepo")
data class Trade(

//        @Id
        var id: Int = 0,
        val bidId: Int = 0,
        val askId: Int = 0,
        val symbol: String?,
        val tradePrice: BigDecimal = BigDecimal(0),
        val timeStamp: Date = Date.from(Instant.now())
)
