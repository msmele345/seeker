package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.AuditLogRepository
import com.mitchmele.seeker.model.Trade
import com.mitchmele.seeker.model.AuditLog
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.springframework.integration.store.MessageGroup
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.support.MessageBuilder
import java.math.BigDecimal
import java.util.*

internal class AuditLogRepositoryTest {

    private val mockRepo: AuditLogRepository = mock()

    private val subject = AuditLogService(mockRepo)

    @Test
    internal fun `save - saves to audit repo`() {

        val inputMessage = MessageBuilder.withPayload(Trade(symbol = "ABC"))
                .setHeader("symbol", "ABC")
                .build()

        val exception = RuntimeException("bad error")

        val expected = AuditLog(symbol = "ABC", message = "bad error", exception = exception)

        subject.save(inputMessage, exception)

        verify(mockRepo).save(expected)
    }
}