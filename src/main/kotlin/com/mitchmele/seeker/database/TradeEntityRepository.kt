package com.mitchmele.seeker.database

import com.mitchmele.seeker.model.TradeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TradeEntityRepository: JpaRepository<TradeEntity, Int>
