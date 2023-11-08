package org.project.async.buffer.config.swagger

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy


@Configuration
class SwaggerConfig {

    @Bean
    @Lazy
    fun springShopOpenAPI(buildProperties: BuildProperties): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Buffer process batch")
                    .description("implements solution async and sync")
                    .version("v${buildProperties.version}")
                    .license(License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Git Project")
                    .url("https://github.com/leltonborges/buffer_async_read_writer")
            )
    }

}
