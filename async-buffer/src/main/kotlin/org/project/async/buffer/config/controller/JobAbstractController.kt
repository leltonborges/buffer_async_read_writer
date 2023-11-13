package org.project.async.buffer.config.controller

import org.project.async.buffer.core.pattern.vo.FileInfo
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.http.ResponseEntity

abstract class JobAbstractController(
    private val job: Job,
    private val jobLauncher: JobLauncher,
) {
    protected fun convertToJobParameters(vararg params: Pair<String, String>): JobParameters {
        val parameters = params.toList()
                .associate(parsePair())
        return JobParameters(parameters)
    }

    private fun parsePair(): (Pair<String, String>) -> Pair<String, JobParameter<String>> = {
        Pair(it.first, JobParameter(it.second, String::class.java))
    }

    protected fun convertToJobParameters(fileInfo: FileInfo): JobParameters {
        val jobParametersBuilder = JobParametersBuilder()
        with(fileInfo) {
            fileDelimiter.let { jobParametersBuilder.addString("fileDelimiter", it) }
            processDate.let { jobParametersBuilder.addLocalDate("processDate", it) }
            fileEncoding.let { jobParametersBuilder.addString("fileEncoding", it) }
            filePath.takeIf { it.isNotBlank() }
                    ?.let { jobParametersBuilder.addString("filePath", it) }
            jobParametersBuilder.addLong("unique", System.currentTimeMillis())
        }
        return jobParametersBuilder.toJobParameters();
    }

    protected fun executeJob(jobParameters: JobParameters): ResponseEntity<JobInstance> {
        val jobExecution = jobLauncher.run(this.job, jobParameters)
        return ResponseEntity.ok(jobExecution.jobInstance)
    }
}