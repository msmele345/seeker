package com.mitchmele.seeker.config

import com.mitchmele.seeker.services.TradeMessageHandler
import com.mitchmele.seeker.services.AuditLogService
import com.mitchmele.seeker.services.TradeTransformer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.core.MessagingTemplate
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows


@Configuration
@EnableIntegration
@EnableBinding(ChannelBinder::class)
@ComponentScan("com.mitchmele.*")
class MessageConfig {

    @Bean
    @Qualifier("errorQueue")
    internal fun errorQueue() = DirectChannel()

    @Bean
    internal fun messagingTemplate(): MessagingTemplate {
        return MessagingTemplate()
    }

    @Bean
    internal fun inboundFlow(
            tradeTransformer: TradeTransformer,
            tradeMessageHandler: TradeMessageHandler
    ): IntegrationFlow {
        return IntegrationFlows.from(Sink.INPUT)
                .transform(tradeTransformer)
                .handle(tradeMessageHandler)
                .channel("groupSave")
                .get()
    }


    @Bean
    internal fun batchPersistenceFlow(
            auditLogService: AuditLogService
    ): IntegrationFlow {
        return IntegrationFlows.from("groupSave")
                .log<Any> { println("HEADERS at BATCH " + it.headers) }
//                .log<Any> { "AFtER AGG" }
                .log("MADE IT HERE")
                .get()
    }
}