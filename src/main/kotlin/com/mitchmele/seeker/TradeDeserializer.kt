package com.mitchmele.seeker

import com.fasterxml.jackson.databind.ObjectMapper
import com.mitchmele.seeker.model.Trade
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serializer
import org.springframework.messaging.Message

class TradeDeserializer : Deserializer<Trade> {

    override fun deserialize(topic: String?, data: ByteArray): Trade? {
        var retVal: Trade? = null
        val objectMapper = ObjectMapper()
        try {
            objectMapper.readValue(data, Trade::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retVal
    }
}