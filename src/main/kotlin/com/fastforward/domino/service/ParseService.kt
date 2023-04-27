package com.fastforward.domino.service

import com.fastforward.domino.dto.StockDataDto
import com.fastforward.domino.dto.StockRawDataDto
import org.springframework.stereotype.Component

@Component
class ParseService {

    fun parseStockData(data: StockRawDataDto?): List<StockDataDto> {
        data?.let {
            val result = data.chart.result[0]
            return result.timestamp
                .mapIndexed{ index, timestamp -> StockDataDto(
                    timestamp,
                    result.indicators.quote[0],
                    index
                )
                }.toList()
        }
        return ArrayList()
    }
}