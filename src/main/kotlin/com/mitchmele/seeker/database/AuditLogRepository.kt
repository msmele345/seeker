package com.mitchmele.seeker.database

import com.mitchmele.seeker.model.AuditLog
import org.springframework.data.mongodb.repository.MongoRepository

interface AuditLogRepository: MongoRepository<AuditLog, Int>