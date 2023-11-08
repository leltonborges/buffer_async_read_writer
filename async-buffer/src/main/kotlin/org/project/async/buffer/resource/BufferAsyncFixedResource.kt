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
class BufferAsyncFixedResource(
    @Qualifier("jobFileFixed") private val job: Job,
    jobLauncher: JobLauncher,
) : JobAbstractController(job, jobLauncher) {

    @Tag(name = "async")
    @Tag(name = "fileFixedSize")
    @Tag(name = "OK")
    @PostMapping(path = ["/async/reader/file/fixedsize"])
    fun asyncBufferReaderFileFixedSize(
        @RequestBody fileInfo: FileInfo,
    ): ResponseEntity<JobInstance> {
        val jobParameters = convertToJobParameters(fileInfo)
        return executeJob(jobParameters)
    }
}