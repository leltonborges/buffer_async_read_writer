package org.project.async.buffer.config.property

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BufferProperties {

    @Bean("bufferSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.buffer")
    fun batchProperties(): DataSourceProperties {
        return DataSourceProperties();
    }

}