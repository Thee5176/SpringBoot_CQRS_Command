package com.thee5176.ledger_command.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;

@Configuration
public class LoggingConfig {

        @Value("${management.otlp.logging.endpoint:http://localhost:4317}")
        private String otlpEndpoint;

        @Bean
        public OpenTelemetry openTelemetry() {
        Resource resource = Resource.getDefault()
                .merge(Resource.create(Attributes.of(AttributeKey.stringKey("service.name"), "ledger-command")));

        SdkLoggerProvider loggerProvider = SdkLoggerProvider.builder()
                .setResource(resource)
                .addLogRecordProcessor(BatchLogRecordProcessor.builder(
                        OtlpGrpcLogRecordExporter.builder()
                                .setEndpoint(otlpEndpoint)
                                .build())
                        .build())
                .build();

        OpenTelemetrySdk sdk = OpenTelemetrySdk.builder()
                .setLoggerProvider(loggerProvider)
                .build();

        OpenTelemetryAppender.install(sdk);
        return sdk;
        }
}
