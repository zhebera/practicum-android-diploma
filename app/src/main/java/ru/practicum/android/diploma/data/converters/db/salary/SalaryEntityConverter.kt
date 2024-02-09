package ru.practicum.android.diploma.data.converters.db.salary

import ru.practicum.android.diploma.data.db.entity.SalaryEntity
import ru.practicum.android.diploma.domain.models.Salary

object SalaryEntityConverter {

    fun map(salaryEntity: SalaryEntity?): Salary {
        return Salary(
            salaryEntity?.currency,
            salaryEntity?.from,
            salaryEntity?.gross,
            salaryEntity?.to
        )
    }

    fun map(salary: Salary?): SalaryEntity {
        return SalaryEntity(
            salary?.currency,
            salary?.from,
            salary?.gross,
            salary?.to
        )
    }
}
