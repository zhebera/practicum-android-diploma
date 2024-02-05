package ru.practicum.android.diploma.ui.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country

class CountriesViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_place, parent, false)) {
    private val place = itemView.findViewById<TextView>(R.id.place_name)

    fun bind(country: Country) {
        place.text = country.name
    }
}
