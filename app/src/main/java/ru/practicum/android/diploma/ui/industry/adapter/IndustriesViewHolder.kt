package ru.practicum.android.diploma.ui.industry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesViewHolder(
    parent: ViewGroup,
    val clickListener: IndustriesAdapter.IndustriesClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.card_sector, parent, false)
) {

    private val industryView = itemView.findViewById<CheckBox>(R.id.sector_radio_button)

    fun bind(industry: Industry) {
        industryView.text = industry.name
        industryView.isChecked = industry.isChecked

        industryView.setOnClickListener {
            industry.isChecked = !industry.isChecked
            clickListener.onClick(industry)
        }
    }

}
