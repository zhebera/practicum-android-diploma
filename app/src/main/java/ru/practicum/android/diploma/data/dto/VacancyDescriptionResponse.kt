package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.KeySkill
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule

class VacancyDescriptionResponse(
    val id: String,                     //id
    val name: String,                   //название
    val salary: Salary?,                 //оплата
    val employer: Employer,             //работодатель
    val area: Area,                     //место
    val address: Address,               //адрес
    val experience: Experience,         //опыт
    val employment: Employment,         //полная/частичная занятость
    val schedule: Schedule,             //офис/удаленка
    val branded_description: String?,    //описании вакансии
    val key_skills: List<KeySkill>,     //ключевые навыки
    val contacts: Contacts?,             //контакты
    val description: String
) : Response()
