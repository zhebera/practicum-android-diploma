package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDescriptionResponse(
    val id: String,
    val name: String,
    val salary: SalaryResponse?,
    val employer: EmployerResponse?,
    val area: AreaResponse?,
    val address: AddressResponse?,
    val experience: ExperienceResponse?,
    val employment: EmploymentResponse?,
    val schedule: ScheduleResponse?,
    @SerializedName("key_skills") val keySkills: List<KeySkillResponse>,
    val contacts: ContactsResponse?,
    val description: String
) : Response()
