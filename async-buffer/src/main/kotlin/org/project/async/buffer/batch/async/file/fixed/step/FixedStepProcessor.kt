package org.project.async.buffer.batch.async.file.fixed.step

import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component("fixedStepProcessor")
@StepScope
class FixedStepProcessor : ItemProcessor<PersonDTO, PersonDTO> {

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