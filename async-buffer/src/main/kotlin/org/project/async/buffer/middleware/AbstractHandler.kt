package org.project.async.buffer.middleware

abstract class AbstractHandler<T, R> {
    private lateinit var next: AbstractHandler<T, R>;

    private fun isNext(): Boolean {
        return this::next.isInitialized;
    }

    private fun currencyProcess(item: T): R {
        if (isInvalid(item)) return defaultReturnProcess()

        val process = process(item)
        return if (isNext()) this.next.processHandler(item) else process
    }


    fun nextHander(handler: AbstractHandler<T, R>) {
        this.next = handler
    }

    protected abstract fun defaultReturnProcess(): R

    protected abstract fun process(item: T): R

    protected abstract fun isInvalid(item: T): Boolean

    fun processHandler(item: T): R {
        return currencyProcess(item)
    }

    companion object {
        @JvmStatic
        protected fun <T, R, V : AbstractHandler<T, R>> link(vararg handlers: V): V {
            for (index in 0 until handlers.size - 1) {
                val currency = handlers[index]
                currency.nextHander(handlers[index + 1])
            }
            return handlers[0]
        }
    }
}
