package org.project.async.buffer.batch.async.db.fixed.step

import org.project.async.buffer.core.enums.PersonFixed
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.project.async.buffer.core.utils.DateUtils
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@StepScope
@Component("fixedStepProcessor")
class FixedStepProcessor : ItemProcessor<Person, PersonVO> {

    override fun process(item: Person): PersonVO? {
        return if (item.isValid()) item.toPersonVO() else null
    }

    private fun Person.toPersonVO() = PersonVO(
        name = formatName(name),
        document = formatDocument(document),
        typeDocument = formatDocumentType(typeDocument),
        age = formatAge(age),
        login = login?.login.orEmpty().let(::formatLogin),
        password = login?.password.orEmpty().let(::formatPassword),
        dtLasUpdatePass = login?.dtLastUpdatePass?.let(::formatDate),
        dtLastAcess = login?.dtLastAcess?.let(::formatDate),
        dtCreatedAt = login?.dtCreatedAt?.let(::formatDate)
    )

    private fun formatName(name: String) = PersonFixed.NAME.processField(name)

    private fun formatDocument(document: String) = PersonFixed.DOCUMENT.processField(document)

    private fun formatAge(age: Int) = PersonFixed.AGE.processField(age)

    private fun formatDocumentType(type: Int) = PersonFixed.DOCUMENT_TYPE.processField(type)

    private fun formatLogin(login: String) = PersonFixed.LOGIN.processField(login)

    private fun formatPassword(password: String) = PersonFixed.PASSWORD.processField(password)

    private fun formatDate(time: LocalDateTime) =
        PersonFixed.DT_LAST_UPDATE.processField(time, DateUtils.formattedToString)

    companion object {
        @JvmStatic
        private fun Person.isValid() = name.isNotBlank() && document.isNotBlank()
    }
}