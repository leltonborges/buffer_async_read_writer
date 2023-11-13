package org.project.async.buffer.batch.async.file.fixed.step

import org.project.async.buffer.core.enums.PersonFixed
import org.project.async.buffer.core.pattern.dto.PersonDTO
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component("fixedStepProcessor")
@StepScope
class FixedStepProcessor : ItemProcessor<PersonDTO, PersonVO> {

    override fun process(item: PersonDTO): PersonVO? {
        return if (isInvalid(item)) null else processItem(item)
    }

    private fun processItem(item: PersonDTO): PersonVO {
        return PersonVO(formatName(item.name),
                        formatDocument(item.document),
                        formatDocumentType(item.typeDocument),
                        formatAge(item.age),
                        formatLogin(item.login.login),
                        formatPassword(item.login.password),
                        item.login.dtLastUpdatePass?.let { formatDate(it) },
                        item.login.dtLastAcess?.let { formatDate(it) },
                        item.login.dtCreatedAt?.let { formatDate(it) },
                        )
    }

    private fun formatName(name: String) = PersonFixed.NAME.processField(name)

    private fun formatDocument(document: String) = PersonFixed.DOCUMENT.processField(document)

    private fun formatAge(age: Int) = PersonFixed.AGE.processField(age)

    private fun formatDocumentType(type: Int) = PersonFixed.DOCUMENT_TYPE.processField(type)

    private fun formatLogin(login: String) = PersonFixed.LOGIN.processField(login)

    private fun formatPassword(password: String) = PersonFixed.PASSWORD.processField(password)

    private fun formatDate(time: LocalDateTime) = PersonFixed.DT_LAST_UPDATE.processField(time, "yyyyMMdd")

    companion object {
        @JvmStatic
        private fun isInvalid(value: PersonDTO): Boolean {
            return value.name.isBlank() || value.document.isBlank()
        }
    }
}