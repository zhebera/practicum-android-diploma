package ru.practicum.android.diploma.data.impl.details

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.details.DetailsRepository

class DetailsRepositoryImpl(context: Context) : DetailsRepository {

    private val androidContext by lazy { context }

    override fun shareLink(link: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        androidContext.startActivity(shareIntent)
    }

    override fun openEmail(email: String) {
        val emailIntent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto: $email")
            putExtra(Intent.EXTRA_EMAIL, email)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        try {
            androidContext.startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            print(e.message)
            Toast.makeText(
                androidContext,
                androidContext.getString(R.string.no_email_toast),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun openPhone(phone: String) {
        val callIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phone")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        androidContext.startActivity(callIntent)
    }
}
