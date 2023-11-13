package org.project.async.buffer.batch.utils

import org.project.async.buffer.config.property.ThreadProperties
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ThreadPoolExecutor


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
}