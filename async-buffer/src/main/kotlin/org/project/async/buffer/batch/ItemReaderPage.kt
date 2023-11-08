package org.project.async.buffer.batch

import org.springframework.batch.item.ItemReader
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

abstract class ItemReaderPage<T>() : ItemReader<T>,
                                     StepExecutionListenerBuffer() {
    private lateinit var pageable: Pageable
    private lateinit var iterator: Iterator<T>;
    private var isLastPage = false

    abstract fun startPage(): Pageable

    abstract fun getData(pageable: Pageable): Page<T>

    override fun read(): T? {
        if (!::iterator.isInitialized || (!iterator.hasNext() && !isLastPage)) {
            nextPage()
        }

        if (isLastPage && !iterator.hasNext()) {
            return null
        }

        return iterator.next()
    }

    private fun nextPage() {
        if (!::pageable.isInitialized) pageable = startPage()

        val data = getData(this.pageable)
        this.pageable = data.nextPageable()
        this.iterator = data.iterator()
        this.isLastPage = data.isLast
    }
}