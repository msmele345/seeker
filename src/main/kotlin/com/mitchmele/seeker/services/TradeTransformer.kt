package com.mitchmele.seeker.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.mitchmele.seeker.model.Trade
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.integration.transformer.AbstractTransformer
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class TradeTransformer(
        private val objectMapper: ObjectMapper
) : AbstractTransformer() {

    override fun doTransform(message: Message<*>?): Any? {
        val logger: Logger = LoggerFactory.getLogger(TradeTransformer::class.java)

        return message?.let { messageIn ->
            val payload = messageIn.payloadAsString()
            try {
                objectMapper.readValue(payload, Trade::class.java)
                        .also { logger.info("Successfully transformed payload for trade: {}", it) }
            } catch (e: Exception) {
                throw e
            }
        }

    }
}

fun Message<*>.payloadAsString() =
        when (this.payload) {
            is ByteArray -> String(this.payload as ByteArray)
            is String -> this.payload as String
            else -> this.payload.toString()
        }