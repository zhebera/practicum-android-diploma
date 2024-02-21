package ru.practicum.android.diploma.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(private val clickListener: VacancyClickListener) :
    RecyclerView.Adapter<VacancyViewHolder>() {

    private var vacancies = ArrayList<Vacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder =
        VacancyViewHolder(parent, clickListener)

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancies[position])
    }

    override fun getItemCount(): Int = vacancies.size

    fun interface VacancyClickListener {
        fun onClick(data: Vacancy)
    }

    fun setData(data: List<Vacancy>) {
        vacancies.clear()
        vacancies.addAll(data)
        notifyDataSetChanged()
    }

    fun setNewPageData(data: List<Vacancy>) {
        val lastPosition = itemCount
        vacancies.addAll(data)
        notifyItemInserted(lastPosition + 1)
    }

    fun clear() {
        vacancies.clear()
        notifyDataSetChanged()
    }
}
