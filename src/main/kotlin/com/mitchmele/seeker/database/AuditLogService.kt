package com.mitchmele.seeker.database

import com.mitchmele.seeker.model.AuditLog
import org.springframework.data.mongodb.repository.MongoRepository

interface AuditLogService: MongoRepository<AuditLog, Int>