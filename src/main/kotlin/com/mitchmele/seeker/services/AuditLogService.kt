package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.AuditLogService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.stereotype.Service

@Service
class AuditLogService(
        val auditLogService: AuditLogService
): MessageHandler {


    override fun handleMessage(message: Message<*>) {
        TODO("Not yet implemented")
    }

}