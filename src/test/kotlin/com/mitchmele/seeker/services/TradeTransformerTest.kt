package com.mitchmele.seeker.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mitchmele.seeker.model.Trade
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.messaging.support.MessageBuilder
import java.lang.RuntimeException
import java.math.BigDecimal
import java.util.*

class TradeTransformerTest{

    private val objectMapper = ObjectMapper()
    private val subject = TradeTransformer(objectMapper)

    @Test
    internal fun `doTransform - handles message and transforms payload`() {

        val tradePayload = "{\"id\":10,\"bidId\":2,\"askId\":1,\"symbol\":\"ABC\",\"tradePrice\":21,\"timeStamp\":1602083811248}"

        val incomingMessage = MessageBuilder
                .withPayload(tradePayload)
                .build();

        val actual = subject.transform(incomingMessage)
        assertThat((actual.payload as Trade).id).isEqualTo(10)
        assertThat((actual.payload as Trade).tradePrice).isEqualTo(BigDecimal.valueOf(21))
        assertThat((actual.payload as Trade).symbol).isEqualTo("ABC")
    }

    @Test
    @Disabled
    internal fun `doTransform - throws RuntimeException if mapper fails`() {

        val incomingMessage = MessageBuilder
                .withPayload("tradePayload")
                .build();

        whenever(objectMapper.readValue(any<String>(), Trade::class.java)) doThrow
                RuntimeException("Failed to transform")

        assertThatThrownBy { subject.transform(incomingMessage) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessage("Failed to transform")
    }
}