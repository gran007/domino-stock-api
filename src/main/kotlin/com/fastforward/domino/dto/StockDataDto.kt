package com.fastforward.domino.dto

data class StockDataDto(
    val timeStamp: Long,
    val low: Double,
    val high: Double,
    val open: Double,
    val close: Double,
    val volume: Double) {
    constructor(timeStamp: Long, quote: StockQuote, index: Int) :
            this(timeStamp,
                quote.low[index],
                quote.high[index],
                quote.open[index],
                quote.close[index],
                quote.volume[index])
}