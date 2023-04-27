package com.fastforward.domino.dto

data class StockRawDataDto (
    val chart: StockChart)

data class StockChart (val result: List<StockResult>)

data class StockResult (
    val timestamp: List<Long>,
    val indicators: StockIndicator
)

data class StockIndicator (val quote: List<StockQuote>)

data class StockQuote (
    val high: List<Double>,
    val close: List<Double>,
    val open: List<Double>,
    val volume: List<Double>,
    val low: List<Double>
)