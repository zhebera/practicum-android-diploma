package ru.practicum.android.diploma.ui.industry.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesAdapter(private val clickListener: IndustriesClickListener) :
    RecyclerView.Adapter<IndustriesViewHolder>() {

    private val industries = ArrayList<Industry>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IndustriesViewHolder(parent, clickListener)
    override fun getItemCount() = industries.size

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        holder.bind(industries[position])
    }

    fun interface IndustriesClickListener {
        fun onClick(industry: Industry)
    }

    fun setData(data: List<Industry>) {
        industries.clear()
        industries.addAll(data)
        notifyDataSetChanged()
    }
}
