package org.project.async.buffer.batch

import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing

@EnableBatchProcessing(dataSourceRef = "batchDataSource",
                       transactionManagerRef = "batchTransactionManager",
                       tablePrefix = "TB_BATCH_")
abstract class JobAbstract {
    protected var limitThread: Int? = null

    protected fun jobParametersIncrementer() = JobParametersIncrementer {
        JobParameters(mapOf("times" to JobParameter(System.currentTimeMillis(), Long::class.java)))
    }
}