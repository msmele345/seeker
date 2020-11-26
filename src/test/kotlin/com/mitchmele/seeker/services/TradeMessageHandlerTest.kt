package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.MongoService
import com.mitchmele.seeker.model.Trade
import com.mitchmele.seeker.model.TradeEntity
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TradeMessageHandlerTest {

    private val mongoService: MongoService = mock()
    private val tradeEntityService: TradeEntityService = mock()

    private val subject = TradeMessageHandler(mongoService, tradeEntityService)

    @Test
    fun `handleMessage - should call mongo service to save`() {

        val trade = Trade(
                id = 10,
                symbol = "ABC",
                askId = 1,
                bidId = 2,
                timeStamp = mock(),
                tradePrice = BigDecimal(21.00))

        val entity = TradeEntity(
                id = 10,
                symbol = "ABC",
                askId = 1,
                bidId = 2,
                timeStamp = mock(),
                tradePrice = BigDecimal(21.00))

        subject.handle(trade, null)
        verify(mongoService).saveTrade(trade)

        argumentCaptor<TradeEntity>().let { captor ->
            verify(tradeEntityService).save(captor.capture())
            assertThat(captor.firstValue)
                    .isEqualToIgnoringGivenFields(entity, "hashCode", "timeStamp")
        }
    }
}