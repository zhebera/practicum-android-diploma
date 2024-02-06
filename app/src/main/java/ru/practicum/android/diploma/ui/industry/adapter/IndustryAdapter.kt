package ru.practicum.android.diploma.ui.industry.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Industry

class IndustryAdapter : RecyclerView.Adapter<IndustryViewHolder>() {

    private val industries = arrayListOf<Industry>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IndustryViewHolder(parent)
    override fun getItemCount() = industries.size

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(industries[position])
    }
}
