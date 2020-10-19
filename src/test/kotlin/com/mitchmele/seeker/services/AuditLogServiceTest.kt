package com.mitchmele.seeker.services

import com.mitchmele.seeker.model.Trade
import com.mitchmele.seeker.model.AuditLog
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.springframework.integration.store.MessageGroup
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import java.math.BigDecimal
import java.util.*

internal class AuditLogServiceTest {

    //TODO refactor for audit
    @Test
    internal fun `processMessageGroup - saves Trade Report to repo`() {

        val trade = Trade(id = 10, symbol = "ABC", askId = 1, bidId = 2, timeStamp = Date(), tradePrice = BigDecimal(21.00))
        val trade2 = Trade(id = 10, symbol = "ABC", askId = 1, bidId = 2, timeStamp = Date(), tradePrice = BigDecimal(23.00))


        val group: MessageGroup = mock()

        val trades = listOf(trade, trade2)
        whenever(group.messages) doReturn listOf(trade.toMessage(), trade2.toMessage())


    }

    private fun Trade.toMessage(): Message<Trade> {
        return MessageBuilder.withPayload(this).build()
    }
}