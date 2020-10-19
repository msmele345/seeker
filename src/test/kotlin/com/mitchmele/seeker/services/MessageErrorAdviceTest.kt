package com.mitchmele.seeker.services

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.integration.transformer.MessageTransformationException
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import java.io.IOException

internal class MessageErrorAdviceTest : MessageErrorAdvice(mock(), mock(), mock()) {

    @Test
    internal fun `doInvoke - callback is called`() {

        val inputMessage = MessageBuilder.withPayload("payload").build()

        val callBack: ExecutionCallback = mock()

        doInvoke(callBack, null, inputMessage)

        verify(callBack).execute()
    }

    @Test
    internal fun `doInvoke - returns result of callback if there are no errors`() {
        val inputMessage = MessageBuilder.withPayload("payload").build()

        val callBack: ExecutionCallback = mock()

        whenever(callBack.execute()) doReturn "success"

        val actual = doInvoke(callBack, null, inputMessage)

        assertThat(actual).isEqualTo("success")
    }

    @Test
    internal fun `doInvoke - sends message to error queue if exception is thrown`() {
        val inputMessage = MessageBuilder.withPayload("payload").build()

        val callBack: ExecutionCallback = mock()

        val messageTransformationException = MessageTransformationException("cant transform", IOException("some io exception"))
        whenever(callBack.execute()) doAnswer {
            throw  messageTransformationException
        }


        doInvoke(callBack, null, inputMessage)

        val captor = argumentCaptor<Message<*>>()

        verify(messagingTemplate).send(eq(errorQueue), captor.capture())

        val expectedErrorMsgHeader = "cant transform; nested exception is java.io.IOException: some io exception"
        assertThat(captor.firstValue.headers["errorMessage"]).isEqualTo(expectedErrorMsgHeader)

        verify(auditLogService).save(inputMessage, messageTransformationException)
    }

    @Test
    internal fun `doInvoke - sends message to error queue if general exception is thrown`() {
        val inputMessage = MessageBuilder.withPayload("payload").build()

        val callBack: ExecutionCallback = mock()

        val ex = RuntimeException("something bad")
        whenever(callBack.execute()) doThrow ex

        doInvoke(callBack, null, inputMessage)

        val captor = argumentCaptor<Message<*>>()

        verify(messagingTemplate).send(eq(errorQueue), captor.capture())

        val expectedErrorMsgHeader = "something bad"
        assertThat(captor.firstValue.headers["errorMessage"]).isEqualTo(expectedErrorMsgHeader)

        verify(auditLogService).save(inputMessage, ex)
    }
}