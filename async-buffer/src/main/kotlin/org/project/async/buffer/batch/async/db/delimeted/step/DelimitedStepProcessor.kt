package org.project.async.buffer.batch.async.db.delimeted.step

import org.project.async.buffer.core.enums.PersonFixed
import org.project.async.buffer.core.model.buffer.Login
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.dto.LoginDTO
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.project.async.buffer.core.utils.DateUtils
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@StepScope
@Component("delimitedStepProcessorBuffer")
class DelimitedStepProcessor : ItemProcessor<Person, PersonDTO> {

    override fun process(item: Person): PersonDTO? {
        return if (item.isValid()) item.toPersonDTO() else null
    }

    private fun Person.toPersonDTO() = PersonDTO(
        name = name,
        document = document,
        typeDocument = typeDocument,
        age = age,
        login = login?.toDTO() ?: LoginDTO()
    )


    private fun Login.toDTO() = LoginDTO(
        login,
        password,
        dtLastUpdatePass = dtLastUpdatePass.formatString(),
        dtLastAcess = dtLastAcess.formatString(),
        dtCreatedAt = dtCreatedAt.formatString()
    )

    companion object {
        @JvmStatic
        private fun LocalDateTime?.formatString(): String = this?.let { DateUtils.formattedProcess.format(it) } ?: ""

        @JvmStatic

        private fun Person.isValid() = name.isNotBlank() && document.isNotBlank()
    }
}