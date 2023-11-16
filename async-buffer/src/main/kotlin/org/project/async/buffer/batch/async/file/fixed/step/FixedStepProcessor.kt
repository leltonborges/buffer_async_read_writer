package org.project.async.buffer.batch.async.file.fixed.step;

import org.project.async.buffer.core.model.buffer.Login
import org.project.async.buffer.core.model.buffer.Person
import org.project.async.buffer.core.pattern.vo.PersonVO
import org.project.async.buffer.core.utils.DateUtils
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@StepScope
@Component("fixedStepProcessorDB")
class FixedStepProcessor : ItemProcessor<PersonVO, Person> {

    override fun process(item: PersonVO): Person? = if (item.isValid()) item.toPerson() else null
    private fun PersonVO.isValid() = name.isNotBlank() && document.isNotBlank() && typeDocument != 0

    private fun PersonVO.toPerson() = Person(
        id = null,
        name = name,
        document = document,
        typeDocument = typeDocument,
        age = age,
        login = this.toLogin()
    )

    private fun PersonVO.toLogin(): Login? = if (this.login.isNotBlank()) {
        Login(
            id = null,
            login = this.login,
            password = this.password,
            dtLastUpdatePass = this.dtLasUpdatePass.toLocalDateTime(),
            dtLastAcess = this.dtLastAcess.toLocalDateTime(),
            dtCreatedAt = this.dtCreatedAt.toLocalDateTime()
        )
    } else null

    private fun String?.toLocalDateTime() = this?.let { DateUtils.convertDateFixed(it) }
}