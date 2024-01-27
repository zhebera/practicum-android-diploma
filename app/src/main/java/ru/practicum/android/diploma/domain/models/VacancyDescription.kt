package ru.practicum.android.diploma.domain.models

data class VacancyDescription(
    val id: String,                     //id
    val name: String,                   //название
    val salary: Salary?,                 //оплата
    val employer: Employer?,             //работодатель
    val area: Area?,                     //место
    val address: Address?,               //адрес
    val experience: Experience?,         //опыт
    val employment: Employment?,         //полная/частичная занятость
    val schedule: Schedule?,             //офис/удаленка
    val key_skills: List<KeySkill>,     //ключевые навыки
    val contacts: Contacts?,             //контакты
    val description: String,            //комментарий
)
