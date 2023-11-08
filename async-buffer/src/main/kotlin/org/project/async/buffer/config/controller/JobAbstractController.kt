package org.project.async.buffer.config.controller

import org.project.async.buffer.core.common.Constants.PROCESS_DATE
import org.project.async.buffer.core.pattern.vo.FileInfo
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.http.ResponseEntity
import java.time.LocalDate

abstract class JobAbstractController(
    private val job: Job,
    private val jobLauncher: JobLauncher,
) {
    protected fun convertToJobParameters(vararg params: Pair<String, String>): JobParameters {
        val parameters = params.toList().associate { Pair(it.first, JobParameter(it.second, String::class.java)) }
        return JobParameters(parameters)
    }

    protected fun convertToJobParameters(fileInfo: FileInfo): JobParameters {
        val jobParametersBuilder = JobParametersBuilder()
        with(fileInfo) {
            fileDelimiter.let { jobParametersBuilder.addString("fileDelimiter", it) }
            processDate.let { jobParametersBuilder.addLocalDate("processDate", it) }
            fileEncoding.let { jobParametersBuilder.addString("fileEncoding", it) }
            filePath.takeIf { it.isNotBlank() }?.let { jobParametersBuilder.addString("filePath", it) }
            jobParametersBuilder.addLong("unique", System.currentTimeMillis())
        }
        return jobParametersBuilder.toJobParameters();
    }

    protected fun executeJob(jobParameters: JobParameters): ResponseEntity<JobInstance> {
        val jobExecution = jobLauncher.run(this.job, jobParameters)
        return ResponseEntity.ok(jobExecution.jobInstance)
    }

    private fun parameters(jobParameters: JobParameters): JobParameters {
        val processDate = jobParameters.getString(PROCESS_DATE)
        return if (processDate.isNullOrBlank()) {
            JobParametersBuilder(jobParameters).addLocalDate(PROCESS_DATE, LocalDate.now()).toJobParameters()
        } else jobParameters

    }
}