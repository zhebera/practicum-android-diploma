package ru.practicum.android.diploma.domain.api.details

interface DetailsRepository {

    fun shareLink(link: String)

    fun openEmail(email: String)

    fun openPhone(phone: String)
}
