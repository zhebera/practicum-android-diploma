package ru.practicum.android.diploma.domain.api.search.details

interface SharingInteractor {

    fun shareLink(link: String)

    fun openEmail(email: String)

    fun doCall(phone: String)
}
