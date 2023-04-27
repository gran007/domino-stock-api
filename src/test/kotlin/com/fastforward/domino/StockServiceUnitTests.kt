package com.fastforward.domino

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fastforward.domino.dto.StockDataDto
import com.fastforward.domino.dto.StockRawDataDto
import com.fastforward.domino.entity.StockEntity
import com.fastforward.domino.repository.StockRepository
import com.fastforward.domino.service.ParseService
import com.fastforward.domino.service.StockService
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

class StockServiceUnitTests {

    private val mapper: ObjectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(KotlinModule.Builder().build())

    private val stockCode = "test"
    private val interval = 1
    private val totalDays = 5

    private fun getWebClient(): WebClient {
        val response = mockk<WebClient.ResponseSpec>()
        val spec = mockk<WebClient.RequestHeadersUriSpec<*>>()
        val webClient = mockk<WebClient>()

        val testJson = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[1.0],\"open\":[1.0],\"high\":[1.0],\"volume\":[1]}]}}]}}"
        val testData = mapper.readValue(testJson, StockRawDataDto::class.java)

        every { response.bodyToMono(StockRawDataDto::class.java) } returns Mono.just(testData)
        every { spec.uri("/${stockCode}?interval=${interval}d&range=${totalDays}d") } returns spec
        every { spec.accept(MediaType.APPLICATION_JSON) } returns spec
        every { spec.retrieve() } returns response
        every { webClient.get() } returns spec
        return webClient
    }
    @Test
    fun `stock service should return parsed result`() {

        val compareJson = "[{\"timestamp\":1,\"low\":1.0,\"high\":1.0,\"open\":1.0,\"close\":1.0,\"volume\":1}]"
        val compareData = mapper.readValue(compareJson, object : TypeReference<List<StockDataDto>>() {})
        val mockStockRepository = mockk<StockRepository>()
        val mockWebClient = getWebClient()
        val stockService = StockService(ParseService(), mockStockRepository, mockWebClient)

        every { mockStockRepository.saveAll(any<List<StockEntity>>()) } returns listOf(StockEntity())
        val resultData = stockService.parseAndSaveStockData(stockCode, interval, totalDays)

        compareData shouldBe resultData
    }
}
