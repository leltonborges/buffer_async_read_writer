package org.project.async.buffer.batch

import org.springframework.batch.item.ItemWriter

abstract class AbstractItemWriterStep<T> : ItemWriter<T>,
                                           StepExecutionListenerBuffer() {}