package com.mitchmele.seeker.services

import com.mitchmele.seeker.model.AuditLog
import org.springframework.integration.core.MessagingTemplate
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice
import org.springframework.integration.transformer.MessageTransformationException
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class MessageErrorAdvice(
        val messagingTemplate: MessagingTemplate,
        val errorQueue: MessageChannel,
        val auditLogService: AuditLogService
) : AbstractRequestHandlerAdvice() {

    override fun doInvoke(callback: ExecutionCallback, target: Any?, message: Message<*>): Any? {

        return try {
            callback.execute()
        } catch (ex: MessageTransformationException) {

            ex.cause.let { transformationClause ->
                when (transformationClause) {
                    is IOException -> {
                        auditLogService.save(message, ex)
                        messagingTemplate.send(errorQueue, message.toErrorMessage(ex))
                    }
                }
            }
        } catch (ex: Exception) {
            auditLogService.save(message, ex)
            messagingTemplate.send(errorQueue, message.toErrorMessage(ex))
        }

    }

    private fun <T> Message<T>.toErrorMessage(ex: Throwable): Message<T> = MessageBuilder
            .withPayload(this.payload)
            .copyHeadersIfAbsent(this.headers)
            .setHeader("errorMessage", ex.localizedMessage)
            .build()

}