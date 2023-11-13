package org.project.async.buffer.config.property

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "thread")
@Qualifier("threadProperties")
data class ThreadProperties(
    val core: Int,
    val max: Int,
    val capacity: Int
)
