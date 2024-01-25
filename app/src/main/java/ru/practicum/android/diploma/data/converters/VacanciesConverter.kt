package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacancyDescription

class VacanciesConverter {

    fun convertVacancyDescription(response: VacancyDescriptionResponse) : VacancyDescription {
        return VacancyDescription(
            id = response.id,
            name = response.name,
            salary = response.salary ?: Salary("", 0, true, ""),
            employer = response.employer,
            area = response.area,
            address = response.address,
            experience = response.experience,
            employment = response.employment,
            schedule = response.schedule,
            branded_description = response.branded_description ?: "",
            key_skills = response.key_skills,
            contacts = response.contacts ?: Contacts("", "", emptyList()),
            description = response.description
        )
    }

}
