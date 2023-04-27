package com.fastforward.domino.controller

import com.fastforward.domino.dto.StockDataDto
import com.fastforward.domino.service.DominoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "domino stock info api")
@RestController
@RequestMapping("/domino")
class DominoController(val dominoService: DominoService) {

    @Operation(summary = "samsung stock info")
    @Parameters(
        value = [
            Parameter(name = "interval", description = "interval", example = "1"),
            Parameter(name = "totalDays", description = "totalDays", example = "5")]
    )
    @GetMapping("/samsung/{interval}/{totalDays}")
    fun getSamsungStockInfo(
            @PathVariable("interval") interval: Int,
            @PathVariable("totalDays") totalDays: Int): ResponseEntity<List<StockDataDto>> {
                val data = dominoService.getStockData("005930.KS", interval, totalDays)
        return ResponseEntity(data, HttpStatus.OK)
    }
}