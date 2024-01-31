package ru.practicum.android.diploma.domain.impl.details

import ru.practicum.android.diploma.domain.api.details.DetailsInteractor
import ru.practicum.android.diploma.domain.api.details.DetailsRepository

class DetailsInteractorImpl(private val detailsRepository: DetailsRepository) : DetailsInteractor {

    override fun shareLink(link: String) {
        detailsRepository.shareLink(link)
    }

    override fun openEmail(email: String) {
        detailsRepository.openEmail(email)
    }

    override fun doCall(phone: String) {
        detailsRepository.openPhone(phone)
    }
}
