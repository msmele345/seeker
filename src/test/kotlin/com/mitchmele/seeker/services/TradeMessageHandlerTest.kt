package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.MongoService
import com.mitchmele.seeker.model.Trade
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test
import org.springframework.messaging.support.MessageBuilder
import java.math.BigDecimal
import java.util.*

internal class TradeMessageHandlerTest {

    private val mongoService: MongoService = mock()

    private val subject = TradeMessageHandler(mongoService)

    @Test
    internal fun `handleMessage - should call mongo service to save`() {

    val trade = Trade(
            id = 10,
            symbol = "ABC",
            askId = 1,
            bidId = 2,
            timeStamp = Date(),
            tradePrice = BigDecimal(21.00))

        subject.handle(trade, null)
        verify(mongoService).saveTrade(trade)
    }
}