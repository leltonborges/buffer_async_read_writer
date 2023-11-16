package org.project.async.buffer.batch.async.file.delimeted.step;

import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@StepScope
@Component("stepAsyncDelimitedProcessorDB")
class DelimitedStepProcessor : ItemProcessor<PersonDTO, Person> {

    override fun process(item: PersonDTO): Person? {
        return if (isInvalid(item)) null
        else processItem(item);
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

    private fun processItem(item: PersonDTO): Person {
        return Person(item);
    }
}