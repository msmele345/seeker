package com.mitchmele.seeker.config

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.messaging.SubscribableChannel

interface ChannelBinder {

    @Input(Sink.INPUT)
    fun input(): SubscribableChannel
}