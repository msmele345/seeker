package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.TradeEntityRepository
import com.mitchmele.seeker.model.TradeEntity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test

class TradeEntityServiceTest {

    private val mockRepo: TradeEntityRepository = mock()
    private val subject: TradeEntityService = TradeEntityService(mockRepo)

    @Test
    fun `save - saves entity to sql database`() {

        val trade = TradeEntity(symbol = "ABC")

        subject.save(trade)

        verify(mockRepo).save(trade)
    }
}