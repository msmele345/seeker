package com.mitchmele.seeker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.messaging.Processor
import org.springframework.cloud.stream.test.binder.MessageCollector
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationFlowIT {

    @Autowired
    lateinit var processor: Processor

    @Autowired
    private lateinit var messageCollector: MessageCollector

    @Test
    internal fun `message makes it`() {

        val tradePayload = "{\"id\":10,\"bidId\":2,\"askId\":1,\"symbol\":\"ABC\",\"tradePrice\":21,\"timeStamp\":1602083811248}"

        val incomingMessage = MessageBuilder
                .withPayload(tradePayload)
                .build();

        processor.input().send(incomingMessage)

        val received: Message<*> = messageCollector.forChannel(processor.output()).poll()
        assertThat(received.payload).isEqualTo("")

    }
}