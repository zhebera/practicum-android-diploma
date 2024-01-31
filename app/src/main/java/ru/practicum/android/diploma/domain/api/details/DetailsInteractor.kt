package ru.practicum.android.diploma.domain.api.details

interface DetailsInteractor {

    fun shareLink(link: String)

    fun openEmail(email: String)

    fun doCall(phone: String)
}
