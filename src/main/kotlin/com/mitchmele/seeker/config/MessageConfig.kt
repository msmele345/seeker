package com.mitchmele.seeker.config

import com.mitchmele.seeker.services.TradeMessageHandler
import com.mitchmele.seeker.services.AuditLogService
import com.mitchmele.seeker.services.MessageErrorAdvice
import com.mitchmele.seeker.services.TradeTransformer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.core.MessagingTemplate
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.messaging.Message
import java.util.function.Consumer


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
            tradeMessageHandler: TradeMessageHandler,
            messageErrorAdvice: MessageErrorAdvice
    ): IntegrationFlow {
        return IntegrationFlows.from(Sink.INPUT)
                .transform(tradeTransformer, Consumer { e -> e.advice(messageErrorAdvice) })
                .handle(tradeMessageHandler, Consumer { e -> e.advice(messageErrorAdvice) })
                .get()
    }

    @Bean
    internal fun errorsFlow(): IntegrationFlow {
        return IntegrationFlows.from("groupSave")
                .log<Any> { println("SENDING ERRORS TO RABBITMQ " + it.payload) }
                .log("MADE IT HERE")
                .get()
    }
}