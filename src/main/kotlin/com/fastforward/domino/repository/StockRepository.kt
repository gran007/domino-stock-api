package com.fastforward.domino.repository

import com.fastforward.domino.entity.StockEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository: MongoRepository<StockEntity, String> {
}