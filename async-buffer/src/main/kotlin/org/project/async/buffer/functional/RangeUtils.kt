package org.project.async.buffer.functional

import org.springframework.batch.item.file.transform.Range

fun Range.size(): Int{
    return this.max - this.min + 1
}