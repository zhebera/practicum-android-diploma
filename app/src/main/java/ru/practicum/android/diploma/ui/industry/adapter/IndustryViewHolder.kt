package ru.practicum.android.diploma.ui.industry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewHolder(parent: ViewGroup):
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_sector, parent, false)) {

        val industryName = itemView.findViewById<RadioButton>(R.id.sector_radio_button)

        fun bind(industry: Industry){
            industryName.text = industry.name
        }
}
