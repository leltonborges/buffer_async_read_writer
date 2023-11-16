package org.project.async.buffer.resource

import io.swagger.v3.oas.annotations.tags.Tag
import org.project.async.buffer.config.controller.JobAbstractController
import org.project.async.buffer.core.pattern.vo.FileInfoDelimited
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobInstance
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/async"],
                produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE],
                consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
class BufferAsyncDBDelimitedResource(
    @Qualifier("jobFileDelimitedDB")
    private val job: Job,
    jobLauncher: JobLauncher,
) : JobAbstractController(job, jobLauncher) {

    @Tag(name = "async")
    @Tag(name = "fileDelimited")
    @PostMapping(path = ["/writer/file/delimited"])
    fun asyncBufferReaderDB(
        @RequestBody
        fileInfo: FileInfoDelimited,
    ): ResponseEntity<JobInstance> {
        val jobParameters = convertToJobParameters(fileInfo)
        return executeJob(jobParameters)
    }
}