package org.project.async.buffer.batch.async.file.delimeted.step

import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component("delimitedStepProcessorBuffer")
@StepScope
class DelimitedStepProcessor : ItemProcessor<PersonDTO, PersonDTO> {

    override fun process(item: PersonDTO): PersonDTO? {
        return if (isInvalid(item)) null
        else item
    }

    companion object {
        @JvmStatic
        private fun isInvalid(value: PersonDTO): Boolean {
            var validation: Boolean;
            with(value) {
                validation = name.isBlank()
                validation = document.isBlank()
            }
            return validation
        }
    }
}