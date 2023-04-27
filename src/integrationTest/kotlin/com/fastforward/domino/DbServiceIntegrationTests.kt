package com.fastforward.domino

import com.fastforward.domino.entity.StockEntity
import com.fastforward.domino.repository.StockRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class DbServiceIntegrationTests(
    @Autowired val stockRepository: StockRepository) {

    @Test
    fun `save and delete stock data into mongo db`() {
        val id = "test_id"
        val stockEntity = StockEntity(id, 1, 1.0, 1.0, 1.0, 1.0, 1.0)
        stockRepository.save(stockEntity)
        stockRepository.count() shouldBe 1
        val data = stockRepository.findById(id)
        data shouldNotBe null
        data.get() shouldBe stockEntity
        stockRepository.deleteById(id)
        stockRepository.count() shouldBe 0
    }
}
