package com.mitchmele.seeker.database

import com.mitchmele.seeker.model.Trade
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MongoServiceTest {

    private val mockRepo: TradeRepository = mock()

    private val subject = MongoService(mockRepo)

    @Test
    internal fun `saveTrade - saves trade to repo`() {

        val trade = Trade(
                id = 10,
                symbol = "ABC",
                askId = 1,
                bidId = 2,
                timeStamp = mock(),
                tradePrice = BigDecimal(21.00)
        )

        subject.saveTrade(trade)

        val captor = argumentCaptor<Trade>()
        verify(mockRepo).save(captor.capture())

        assertThat(captor.firstValue).isEqualTo(trade)
    }
}