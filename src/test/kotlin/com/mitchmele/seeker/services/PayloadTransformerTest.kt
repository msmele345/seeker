package com.mitchmele.seeker.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.mitchmele.seeker.model.Trade
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PayloadTransformerTest{

    val subject = PayloadTransformer()

    @Test
    internal fun `transforms byte array payload`() {

        val expected = Trade(symbol = "ABC")
        val payload = ObjectMapper().writeValueAsBytes(expected)

        val actual = subject.transform(payload)
        assertThat(actual).isEqualTo(expected)
    }
}