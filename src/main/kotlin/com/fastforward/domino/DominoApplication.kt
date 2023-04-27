package com.fastforward.domino

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class DominoApplication

fun main(args: Array<String>) {
    runApplication<DominoApplication>(*args)
}
