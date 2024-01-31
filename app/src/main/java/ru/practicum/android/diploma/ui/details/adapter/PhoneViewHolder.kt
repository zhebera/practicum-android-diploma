package ru.practicum.android.diploma.ui.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Phone

class PhoneViewHolder(
    parent: ViewGroup,
    private val phoneClickListener: PhoneAdapter.PhoneClickListener
) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_contact_phone, parent, false)) {

    private val phoneTextView = itemView.findViewById<TextView>(R.id.contact_phone)
    private val commentTextView = itemView.findViewById<TextView>(R.id.comment)
    private val commentContainer = itemView.findViewById<LinearLayout>(R.id.comment_container)

    fun bind(phoneItem: Phone) {
        val phone = phoneItem.number
        val comment = phoneItem.comment

        phoneTextView.text = phone

        comment?.let {
            commentContainer.visibility = View.VISIBLE
            commentTextView.text = comment
        }

        phoneTextView.setOnClickListener {
            phoneClickListener.onClick(phone)
        }
    }

}
