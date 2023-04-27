package com.fastforward.domino.entity

import com.fastforward.domino.dto.StockDataDto
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Document(collection = "stock")
class StockEntity() {

    constructor(stockCode: String, stockDataDto: StockDataDto) : this() {
        this.id = "${stockCode}_${stockDataDto.timestamp}"
        this.timestamp = stockDataDto.timestamp
        this.low = stockDataDto.low
        this.high = stockDataDto.high
        this.open = stockDataDto.open
        this.close = stockDataDto.close
        this.volume = stockDataDto.volume
    }

    @Id
    var id: String? = null

    @Field(name="timestamp")
    var timestamp: Long = 0

    @Field(name="low")
    var low: Double = 0.0

    @Field(name="high")
    var high: Double = 0.0

    @Field(name="open")
    var open: Double = 0.0

    @Field(name="close")
    var close: Double = 0.0

    @Field(name="volume")
    var volume: Double = 0.0
}