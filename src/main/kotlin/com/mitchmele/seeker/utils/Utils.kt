package com.mitchmele.seeker.utils

import org.springframework.messaging.Message

fun Message<*>.payloadAsString() =
        when (this.payload) {
            is ByteArray -> String(this.payload as ByteArray)
            is String -> this.payload as String
            else -> this.payload.toString()
        }