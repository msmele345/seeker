package com.mitchmele.seeker.config

import com.mitchmele.seeker.services.TradeMessageHandler
import com.mitchmele.seeker.services.TradeTransformer
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows


@Configuration
@EnableIntegration
@EnableBinding(ChannelBinder::class)
@ComponentScan("com.mitchmele.*")
class MessageConfig {

    @Bean
    internal fun inboundFlow(
            tradeTransformer: TradeTransformer,
            tradeMessageHandler: TradeMessageHandler
    ): IntegrationFlow {
        return IntegrationFlows.from(Sink.INPUT)
                .transform(tradeTransformer)
                .handle(tradeMessageHandler)
                .log<Any> { println("PAYLOAD AFTER SAVE" + it.payload) }
                .get()
    }
}