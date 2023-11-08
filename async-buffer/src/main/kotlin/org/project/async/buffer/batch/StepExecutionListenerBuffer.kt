package org.project.async.buffer.batch

import org.springframework.batch.core.JobInstance
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.annotation.BeforeStep
import org.springframework.beans.factory.annotation.Value

abstract class StepExecutionListenerBuffer : StepExecutionListener {
    private lateinit var _jobInstance: JobInstance
    private lateinit var _stepExecution: StepExecution

    @Value("#{jobParameters['processDate']}")
    private val processDate: String? = null

    @BeforeStep
    override fun beforeStep(stepExecution: StepExecution) {
        this._jobInstance = stepExecution.jobExecution.jobInstance
        this._stepExecution = stepExecution

        requireNotNull(processDate)
    }

    protected val jobInstance: JobInstance
        get() {
//            check(this::_jobInstance.isInitialized) { "jobInstance nao foi inicializado" }
            return _jobInstance
        }

    protected val stepExecution: StepExecution
        get() {
//            check(this::_jobInstance.isInitialized) { "jobInstance nao foi inicializado" }
            return _stepExecution
        }
}