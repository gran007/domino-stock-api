package com.fastforward.domino

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fastforward.domino.dto.StockDataDto
import com.fastforward.domino.dto.StockRawDataDto
import com.fastforward.domino.service.ParseService
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class ParseServiceTests {

    private val parseService = ParseService()

    private val mapper: ObjectMapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(KotlinModule.Builder().build())

    private fun getData(resultRawData: String): Pair<List<StockDataDto>, List<StockDataDto>> {
        val testData = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[1.0],\"open\":[1.0],\"high\":[1.0],\"volume\":[1]}]}}]}}"
        val parsedData = parseService.parseStockData(mapper.readValue(testData, StockRawDataDto::class.java))
        val resultData: List<StockDataDto> = mapper.readValue(resultRawData, object: TypeReference<List<StockDataDto>>() {})
        return Pair(parsedData, resultData)
    }

    @Test
    fun `after parse same data should be matched`() {
        val resultData = "[{\"timeStamp\":1,\"low\":1.0,\"high\":1.0,\"open\":1.0,\"close\":1.0,\"volume\":1}]"
        val result = getData(resultData)
        result.first shouldBe result.second
    }

    @Test
    fun `not matched timestamp should not be equals`() {
        val resultData = "[{\"timeStamp\":0,\"low\":1.0,\"high\":1.0,\"open\":1.0,\"close\":1.0,\"volume\":1}]"
        val result = getData(resultData)
        result.first shouldNotBe  result.second
    }

    @Test
    fun `not matched low should not be equals`() {
        val resultData = "[{\"timeStamp\":1,\"low\":0.0,\"high\":1.0,\"open\":1.0,\"close\":1.0,\"volume\":1}]"
        val result = getData(resultData)
        result.first shouldNotBe  result.second
    }

    @Test
    fun `not matched high should not be equals`() {
        val resultData = "[{\"timeStamp\":1,\"low\":1.0,\"high\":0.0,\"open\":1.0,\"close\":1.0,\"volume\":1}]"
        val result = getData(resultData)
        result.first shouldNotBe  result.second
    }

    @Test
    fun `not matched open should not be equals`() {
        val resultData = "[{\"timeStamp\":1,\"low\":1.0,\"high\":1.0,\"open\":0.0,\"close\":1.0,\"volume\":1}]"
        val result = getData(resultData)
        result.first shouldNotBe  result.second
    }

    @Test
    fun `not matched close should not be equals`() {
        val resultData = "[{\"timeStamp\":1,\"low\":1.0,\"high\":1.0,\"open\":1.0,\"close\":0.0,\"volume\":1}]"
        val result = getData(resultData)
        result.first shouldNotBe  result.second
    }

    @Test
    fun `not matched volume should not be equals`() {
        val resultData = "[{\"timeStamp\":1,\"low\":1.0,\"high\":1.0,\"open\":1.0,\"close\":1.0,\"volume\":0}]"
        val result = getData(resultData)
        result.first shouldNotBe  result.second
    }
}