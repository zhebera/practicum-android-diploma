package ru.practicum.android.diploma.ui.regions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.countries.adapter.RegionsAdapter

class RegionsViewHolder(
    parent: ViewGroup,
    private val clickListener: RegionsAdapter.RegionClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.card_place, parent, false)
) {
    private val place = itemView.findViewById<TextView>(R.id.place_name)

    fun bind(region: Region) {
        place.text = region.name

        itemView.setOnClickListener {
            clickListener.onClick(region)
        }
    }
}
