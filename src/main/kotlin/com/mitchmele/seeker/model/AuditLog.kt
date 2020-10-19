package com.mitchmele.seeker.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class AuditLog(

        @Id
        val _id: ObjectId? = null,
        val symbol: String? = "",
        val message: String = "",
        val exception: Throwable? = null
)
