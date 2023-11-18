package org.project.async.buffer.config.controller

import org.project.async.buffer.core.common.FileInfo
import org.project.async.buffer.core.pattern.vo.FileInfoDelimited
import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.http.ResponseEntity
import java.nio.charset.Charset

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
            if(this is FileInfoDelimited)
                fileDelimiter.let { jobParametersBuilder.addString("fileDelimiter", it) }

            processDate.let { jobParametersBuilder.addLocalDate("processDate", it) }
            fileEncoding.let { jobParametersBuilder.addString("fileEncoding", it) }
            fileName.let { jobParametersBuilder.addString("fileName", it) }
            fileExtension.let { jobParametersBuilder.addString("fileExtension", it) }
            filePath.takeIf { it.isNotBlank() }
                    ?.let { jobParametersBuilder.addString("filePath", it) }
            separatorLine.let { jobParametersBuilder.addString("separatorLine", it) }
            jobParametersBuilder.addLong("unique", System.currentTimeMillis())
        }
        return jobParametersBuilder.toJobParameters();
    }

    protected fun executeJob(jobParameters: JobParameters): ResponseEntity<JobInstance> {
        val jobExecution = jobLauncher.run(this.job, jobParameters)
        return ResponseEntity.ok(jobExecution.jobInstance)
    }
}

fun main() {
    val valorHexadecimal = "0x0D"
    val valorSemPrefixo = valorHexadecimal.removePrefix("0x")
    val valorByte = valorSemPrefixo.toInt(16).toByte()
    val textoEmCP037 = String(byteArrayOf(valorByte), Charset.forName("CP037"))

    println(textoEmCP037)
}