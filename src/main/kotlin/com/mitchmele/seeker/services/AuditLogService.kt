package com.mitchmele.seeker.services

import com.mitchmele.seeker.database.AuditLogRepository
import com.mitchmele.seeker.model.AuditLog
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class AuditLogService(
        val auditLogRepository: AuditLogRepository
) {

    fun save(message: Message<*>?, ex: Throwable) {

        val symbol = message?.let { it.headers["symbol"].toString() }

        val auditLog = AuditLog(symbol = symbol, message = ex.localizedMessage, exception = ex)

        auditLogRepository.save(auditLog)
    }

}