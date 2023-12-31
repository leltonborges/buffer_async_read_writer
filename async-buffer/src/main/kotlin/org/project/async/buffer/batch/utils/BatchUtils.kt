package org.project.async.buffer.batch.utils

import org.project.async.buffer.config.property.ThreadProperties
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.io.File
import java.util.concurrent.ThreadPoolExecutor
import java.util.regex.Matcher


object BatchUtils {
    @JvmStatic
    fun asyncExecutorProcessor(property: ThreadProperties, namePrefix: String = "task-"): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = property.core
        executor.maxPoolSize = property.max
        executor.queueCapacity = property.capacity
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        executor.setThreadNamePrefix(namePrefix)
        executor.initialize()
        return executor
    }

    @JvmStatic
    fun mountPathFile(pathRoot: String, pathName: String): String {
        return "$pathRoot/$pathName".replace(Regex("[/\\\\]"), Matcher.quoteReplacement(File.separator))
    }

    @JvmStatic
    fun mountPathFile(
        pathRoot: String,
        pathPrefix: String,
        fileName: String,
        extensionName: String?,
        timesName: Boolean = false
    ): String {
        val timeSuffix = if (timesName) "-${System.currentTimeMillis()}" else ""
        val extension = extensionName?.takeIf { it.isNotBlank() }?.let { ".$it" } ?: ""

        return "$pathRoot/$pathPrefix/$fileName$timeSuffix$extension".replace(
            Regex("[/\\\\]"),
            Matcher.quoteReplacement(File.separator)
        )
    }

    @JvmStatic
    fun mountDirectoryFile(
        pathRoot: String,
        pathPrefix: String
    ): String {
        return "$pathRoot/$pathPrefix".replace(Regex("[/\\\\]"), Matcher.quoteReplacement(File.separator))
    }

}