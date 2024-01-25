package ru.practicum.android.diploma.domain.models

data class VacancyDescription(
    val id: String,                     //id
    val name: String,                   //название
    val salary: Salary,                 //оплата
    val employer: Employer,             //работодатель
    val area: Area,                     //место
    val address: Address,               //адрес
    val experience: Experience,         //опыт
    val employment: Employment,         //полная/частичная занятость
    val schedule: Schedule,             //офис/удаленка
    val branded_description: String,    //описании вакансии
    val key_skills: List<KeySkill>,     //ключевые навыки
    val contacts: Contacts,             //контакты
    val description: String,            //комментарий
)

data class Address(
    val building: String,
    val city: String,
    val description: String,
    val lat: Double,
    val lng: Double,
    val street: String
)

data class Area(
    val id: String,
    val name: String,
    val url: String
)


data class Contacts(
    val email: String,
    val name: String,
    val phones: List<Phone>
)

data class Employer(
    val alternate_url: String,
    val blacklisted: Boolean,
    val id: String,
    val logo_urls: LogoUrls,
    val name: String,
    val trusted: Boolean,
    val url: String
)

data class Employment(
    val id: String,
    val name: String
)

data class Experience(
    val id: String,
    val name: String
)

data class KeySkill(
    val name: String
)

data class Salary(
    val currency: String,
    val from: Int,
    val gross: Boolean,
    val to: Any
)

data class Schedule(
    val id: String,
    val name: String
)

data class Phone(
    val city: String,
    val comment: Any,
    val country: String,
    val number: String
)

data class LogoUrls(
    val original: String
)
