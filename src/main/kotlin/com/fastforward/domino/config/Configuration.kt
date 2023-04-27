package com.fastforward.domino.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Configuration {

    @Bean
    fun getWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://query1.finance.yahoo.com/v8/finance/chart")
            .exchangeStrategies(
                ExchangeStrategies.builder()
                    .codecs{ configurer ->configurer.defaultCodecs()
                        .jackson2JsonDecoder(
                            Jackson2JsonDecoder(
                            ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                .registerModule(KotlinModule.Builder().build())
                        ))
                    }.build()
            )
            .build()
    }
}