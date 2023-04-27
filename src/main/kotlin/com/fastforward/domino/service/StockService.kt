package com.fastforward.domino.service

import com.fastforward.domino.dto.StockDataDto
import com.fastforward.domino.dto.StockRawDataDto
import com.fastforward.domino.entity.StockEntity
import com.fastforward.domino.repository.StockRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class StockService(
    val parseService: ParseService,
    val stockRepository: StockRepository,
    val webclient: WebClient) {

    fun parseAndSaveStockData(stockCode: String, interval: Int, totalDays: Int): List<StockDataDto> {
        val jsonData: StockRawDataDto? = webclient.get()
            .uri { uriBuilder -> uriBuilder
                .path("/${stockCode}")
                .queryParam("interval", "${interval}d")
                .queryParam("range", "${totalDays}d")
                .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(StockRawDataDto::class.java)
            .block()
        val stockDataDtoList: List<StockDataDto> = parseService.parseStockData(jsonData)
        val stockEntityList = stockDataDtoList.map {
            StockEntity(stockCode, it)
        }.toList()
        stockRepository.saveAll(stockEntityList)
        return stockDataDtoList
    }


}