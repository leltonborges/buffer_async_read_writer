package org.project.async.buffer.resource

import io.swagger.v3.oas.annotations.tags.Tag
import org.project.async.buffer.config.controller.JobAbstractController
import org.project.async.buffer.core.pattern.vo.FileInfo
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobInstance
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/buffer"],
                produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE],
                consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
class BufferAsyncDelimitedResource(
    @Qualifier("jobFileDelimited") private val job: Job,
    jobLauncher: JobLauncher,
) : JobAbstractController(job, jobLauncher) {

    @Tag(name = "async")
    @PostMapping(path = ["/async/reader/bd"])
    fun asyncBufferReaderDB(): ResponseEntity<JobInstance> {
        return executeJob(convertToJobParameters())
    }

    @Tag(name = "async")
    @Tag(name = "fileDelimited")
    @PostMapping(path = ["/async/writer/file/delimited"], consumes = [APPLICATION_JSON_VALUE])
    @Tag(name = "OK")
    fun asyncBufferReaderFileDelimited(
        @RequestBody fileInfo: FileInfo,
    ): ResponseEntity<JobInstance> {
        val jobParameters = convertToJobParameters(fileInfo)
        return executeJob(jobParameters)
    }

    @Tag(name = "sync")
    @PostMapping(path = ["/sync/reader/bd"])
    fun syncBufferReaderDB(): ResponseEntity<JobInstance> {
        return executeJob(convertToJobParameters())
    }

    @Tag(name = "sync")
    @Tag(name = "fileDelimited")
    @PostMapping(path = ["/sync/reader/file/delimited"])
    fun syncBufferReaderFile(): ResponseEntity<JobInstance> {
        return executeJob(convertToJobParameters())
    }

    @Tag(name = "sync")
    @Tag(name = "fileFixedSize")
    @PostMapping(path = ["/sync/reader/file/fixedsize"])
    fun syncBufferReaderFileFixedSize(): ResponseEntity<JobInstance> {
        return executeJob(convertToJobParameters())
    }

}