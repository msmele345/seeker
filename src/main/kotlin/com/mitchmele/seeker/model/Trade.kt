package com.mitchmele.seeker.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Document(collection = "tradeRepo")
data class Trade(

        @Id
        var id: Int? = null,

        val bidId: Int? = null,
        val askId: Int? = null,
        val symbol: String? = null,
        val tradePrice: BigDecimal? = null,
        val timeStamp: Date? = null
)
