package ru.practicum.android.diploma.data.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.domain.api.details.DetailsRepository

class DetailsRepositoryImpl(private val context: Context) : DetailsRepository {

    override fun shareLink(link: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(shareIntent)
    }

    override fun openEmail(email: String) {
        val emailIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, email)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(emailIntent)
    }

    override fun openPhone(phone: String) {
        val callIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phone")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(callIntent)
    }
}
