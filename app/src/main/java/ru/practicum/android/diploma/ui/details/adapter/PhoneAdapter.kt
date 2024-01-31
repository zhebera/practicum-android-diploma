package ru.practicum.android.diploma.ui.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Phone

class PhoneAdapter(private val clickListener: PhoneClickListener) :
    RecyclerView.Adapter<PhoneViewHolder>() {

    private var contacts = ArrayList<Phone>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder =
        PhoneViewHolder(parent, clickListener)

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    fun interface PhoneClickListener {
        fun onClick(data: String)
    }

    fun setData(data: List<Phone>) {
        contacts.addAll(data)
        notifyDataSetChanged()
    }

}
