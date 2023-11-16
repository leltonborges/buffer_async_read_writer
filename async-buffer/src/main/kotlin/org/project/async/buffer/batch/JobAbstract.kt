package org.project.async.buffer.batch

import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.beans.factory.annotation.Value

@EnableBatchProcessing(
    dataSourceRef = "batchDataSource",
    transactionManagerRef = "batchTransactionManager",
    tablePrefix = "TB_BATCH_"
)
abstract class JobAbstract {
    protected var limitThread: Int? = null

    @Value("\${job.chuck.default}")
    protected var chuckDefault: Int = 0

    protected fun jobParametersIncrementer() = JobParametersIncrementer {
        JobParameters(mapOf("times" to JobParameter(System.currentTimeMillis(), Long::class.java)))
    }
}