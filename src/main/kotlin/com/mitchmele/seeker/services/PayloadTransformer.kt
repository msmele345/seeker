package com.mitchmele.seeker.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.mitchmele.seeker.model.Trade
import org.springframework.integration.transformer.AbstractTransformer
import org.springframework.integration.transformer.GenericTransformer
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class PayloadTransformer : GenericTransformer<ByteArray, Trade> {

    private val objectMapper = ObjectMapper()

    override fun transform(source: ByteArray?): Trade? {
        return source?.let {
            try {
                objectMapper.readValue(source, Trade::class.java)
            } catch (e :Exception) {
                return null
            }
        }
    }
}

fun Message<*>.payloadAsString() =
        when {
            this.payload is ByteArray -> String(this.payload as ByteArray)
            this.payload is String -> this.payload as String
            else -> this.payload.toString()
        }

