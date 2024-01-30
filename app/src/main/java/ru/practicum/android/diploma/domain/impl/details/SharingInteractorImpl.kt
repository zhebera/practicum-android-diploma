package ru.practicum.android.diploma.domain.impl.details

import ru.practicum.android.diploma.domain.api.search.details.ExternalNavigator
import ru.practicum.android.diploma.domain.api.search.details.SharingInteractor

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor {

    override fun shareLink(link: String) {
        externalNavigator.shareLink(link)
    }

    override fun openEmail(email: String) {
        externalNavigator.openEmail(email)
    }

    override fun doCall(phone: String) {
        externalNavigator.openPhone(phone)
    }
}
