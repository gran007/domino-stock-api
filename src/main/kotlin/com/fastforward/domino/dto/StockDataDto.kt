package com.fastforward.domino.dto

data class StockDataDto(
    val timestamp: Long,
    val low: Double,
    val high: Double,
    val open: Double,
    val close: Double,
    val volume: Double) {
    constructor(timestamp: Long, quote: StockQuote, index: Int) :
            this(timestamp,
                quote.low[index],
                quote.high[index],
                quote.open[index],
                quote.close[index],
                quote.volume[index])
}