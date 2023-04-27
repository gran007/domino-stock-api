package com.fastforward.domino.entity

import com.fastforward.domino.dto.StockDataDto
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Document(collection = "stock")
class StockEntity(
    @Id
    var id: String? = null,

    @Field(name="timestamp")
    var timestamp: Long = 0,

    @Field(name="low")
    var low: Double = 0.0,

    @Field(name="high")
    var high: Double = 0.0,

    @Field(name="open")
    var open: Double = 0.0,

    @Field(name="close")
    var close: Double = 0.0,

    @Field(name="volume")
    var volume: Double = 0.0,
    ) {

    constructor(stockCode: String, stockDataDto: StockDataDto) : this() {
        this.id = "${stockCode}_${stockDataDto.timestamp}"
        this.timestamp = stockDataDto.timestamp
        this.low = stockDataDto.low
        this.high = stockDataDto.high
        this.open = stockDataDto.open
        this.close = stockDataDto.close
        this.volume = stockDataDto.volume
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockEntity
        if (id != other.id) return false
        if (timestamp != other.timestamp) return false
        if (low != other.low) return false
        if (high != other.high) return false
        if (open != other.open) return false
        if (close != other.close) return false
        return volume == other.volume
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + low.hashCode()
        result = 31 * result + high.hashCode()
        result = 31 * result + open.hashCode()
        result = 31 * result + close.hashCode()
        result = 31 * result + volume.hashCode()
        return result
    }
}