package com.mitchmele.seeker.model

import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
data class TradeEntity(

        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        val symbol: String? = null,
        val bidId: Int? = null,
        val askId: Int? = null,

        @Column(name = "trade_price")
        val tradePrice: BigDecimal? = null,

        @CreationTimestamp
        @Column(name = "CREATED_TS", updatable = false)
        val timeStamp: Date? = null
)