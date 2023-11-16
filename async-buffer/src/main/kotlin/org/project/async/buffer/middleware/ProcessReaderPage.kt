package org.project.async.buffer.middleware

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


abstract class ProcessReaderPage<T, R, V> {
    open fun readerData(item: V, pageable: Pageable): Page<R> {
        val data = primarySearch(item, pageable)
        val handler = processHandler(data);
        return data.map { handler.processHandler(it) }
    }

    protected abstract fun processHandler(page: Page<T>): AbstractHandler<T, R>
    protected abstract fun primarySearch(item: V, pageable: Pageable): Page<T>
}