package org.project.async.buffer.functional

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

inline fun <reified T> T.logger(name: String): Logger {
    return LoggerFactory.getLogger(name)
}

fun logInfoWriter(stopWatch: StopWatch, size: Int, logger: Logger) {
    logger.info("WRITER: $size REGISTRE IN ${stopWatch.totalTimeMillis} MS")
}