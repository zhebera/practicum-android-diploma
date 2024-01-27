package ru.practicum.android.diploma.data.dto

data class VacancyDescriptionResponse(
    val id: String,                     //id
    val name: String,                   //название
    val salary: SalaryResponse?,                 //оплата
    val employer: EmployerResponse?,             //работодатель
    val area: AreaResponse?,                     //место
    val address: AddressResponse?,               //адрес
    val experience: ExperienceResponse?,         //опыт
    val employment: EmploymentResponse?,         //полная/частичная занятость
    val schedule: ScheduleResponse?,             //офис/удаленка
    val key_skills: List<KeySkillResponse>,     //ключевые навыки
    val contacts: ContactsResponse?,             //контакты
    val description: String
) : Response()
