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

    private var resultData: List<StockDataDto>

    init {
        val resultJson = "[{\"timeStamp\":1,\"low\":1.0,\"high\":1.0,\"open\":1.0,\"close\":1.0,\"volume\":1}]"
        resultData = mapper.readValue(resultJson, object : TypeReference<List<StockDataDto>>() {})
    }

    @Test
    fun `after parse same multiple data should be matched`() {
        val multipleResultJson = "[{\"timeStamp\":1,\"low\":1.0,\"high\":1.0,\"open\":1.0,\"close\":1.0,\"volume\":1},{\"timeStamp\":2,\"low\":2.0,\"high\":2.0,\"open\":2.0,\"close\":2.0,\"volume\":2}]"
        val multipleResultData = mapper.readValue(multipleResultJson, object : TypeReference<List<StockDataDto>>() {})

        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[1,2],\"indicators\":{\"quote\":[{\"close\":[1.0,2.0],\"low\":[1.0,2.0],\"open\":[1.0,2.0],\"high\":[1.0,2.0],\"volume\":[1,2]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldBe multipleResultData
    }

    @Test
    fun `after parse same single data should be matched`() {
        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[1.0],\"open\":[1.0],\"high\":[1.0],\"volume\":[1]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldBe resultData
    }

    @Test
    fun `not matched timestamp should not be equals`() {
        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[0],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[1.0],\"open\":[1.0],\"high\":[1.0],\"volume\":[1]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldNotBe resultData
    }

    @Test
    fun `not matched low should not be equals`() {
        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[0.0],\"open\":[1.0],\"high\":[1.0],\"volume\":[1]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldNotBe resultData
    }

    @Test
    fun `not matched high should not be equals`() {
        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[1.0],\"open\":[1.0],\"high\":[0.0],\"volume\":[1]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldNotBe resultData
    }

    @Test
    fun `not matched open should not be equals`() {
        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[1.0],\"open\":[0.0],\"high\":[1.0],\"volume\":[1]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldNotBe resultData
    }

    @Test
    fun `not matched close should not be equals`() {
        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[0.0],\"low\":[1.0],\"open\":[1.0],\"high\":[1.0],\"volume\":[1]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldNotBe resultData
    }

    @Test
    fun `not matched volume should not be equals`() {
        val compareJson = "{\"chart\":{\"result\":[{\"timestamp\":[1],\"indicators\":{\"quote\":[{\"close\":[1.0],\"low\":[1.0],\"open\":[1.0],\"high\":[1.0],\"volume\":[0]}]}}]}}"
        val compareData = parseService.parseStockData(
            mapper.readValue(compareJson, StockRawDataDto::class.java))
        compareData shouldNotBe resultData
    }
}
