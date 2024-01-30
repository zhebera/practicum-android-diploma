package ru.practicum.android.diploma.domain.api.search.details

interface ExternalNavigator {

    fun shareLink(link: String)

    fun openEmail(email: String)

    fun openPhone(phone: String)
}
