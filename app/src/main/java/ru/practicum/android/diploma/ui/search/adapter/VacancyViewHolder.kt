package ru.practicum.android.diploma.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.glide
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class VacancyViewHolder(
    parent: ViewGroup,
    private val clickListener: VacancyAdapter.VacancyClickListener
) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_vacancy_list, parent, false)) {
    private val radius = itemView.resources.getDimensionPixelSize(R.dimen.card_vacancy_list_margin_start_12dp)
    private val vacancyTitle = itemView.findViewById<TextView>(R.id.vacancy_title)
    private val companyTitle = itemView.findViewById<TextView>(R.id.company_title)
    private val vacancySalary = itemView.findViewById<TextView>(R.id.vacancy_salary)
    private val imageCompany = itemView.findViewById<ImageView>(R.id.image_company)
    fun bind(vacancy: Vacancy) {
        val cityName = vacancy.area.name
        val vacancyTitleText = "${vacancy.name}, $cityName"
        vacancyTitle.text = vacancyTitleText
        companyTitle.text = vacancy.employer.name
        vacancySalary.text = parseSalary(
            vacancy.salary?.from,
            vacancy.salary?.to,
            vacancy.salary?.currency
        )

        vacancy.employer.logoUrls?.original?.let { glide(itemView.context, it, imageCompany, RoundedCorners(radius)) }

        itemView.setOnClickListener {
            clickListener.onClick(vacancy)
        }
    }

    private fun formatSalary(value: Int): String {
        val decimalFormat = DecimalFormat("###,###,###,###,###", DecimalFormatSymbols(Locale.ENGLISH))
        return decimalFormat.format(value).replace(",", " ")
    }

    private fun parseSalary(
        from: Int?,
        to: Int?,
        currency: String?
    ): String {
        val usedCurrency = currency ?: ""
        return if (from == null || to == null) {
            if (from != null) {
                itemView.resources.getString(R.string.salary_from, formatSalary(from), usedCurrency)
            } else if (to != null) {
                itemView.resources.getString(R.string.salary_to, formatSalary(to), usedCurrency)
            } else {
                itemView.resources.getString(R.string.salary_not_specified)
            }
        } else {
            itemView.resources.getString(
                R.string.salary_from_to,
                formatSalary(from),
                formatSalary(to),
                usedCurrency
            )
        }
    }
}
