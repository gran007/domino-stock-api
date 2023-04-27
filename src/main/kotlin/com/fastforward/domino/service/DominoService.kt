package com.fastforward.domino.service

import com.fastforward.domino.dto.StockDataDto
import com.fastforward.domino.dto.StockRawDataDto
import com.fastforward.domino.repository.DominoRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient


@Service
class DominoService(
    val parseService: ParseService,
    val dominoRepository: DominoRepository,
    val webclient: WebClient) {

    fun getStockData(stockCode: String, interval: Int, totalDays: Int): List<StockDataDto> {
        val data: StockRawDataDto? = webclient.get()
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
        return parseService.parseStockData(data)
    }


}