package ru.practicum.android.diploma.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.CardVacancyListBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class VacancyAdapter(private val clickListener: VacancyClickListener) :
    RecyclerView.Adapter<VacancyViewHolder>() {

    private var dataList = ArrayList<Vacancy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view =
            LayoutInflater.from(parent.context)
        return VacancyViewHolder(CardVacancyListBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            clickListener.onClick(dataList[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun interface VacancyClickListener {
        fun onClick(data: Vacancy)
    }

    fun setData(data: List<Vacancy>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }
}

class VacancyViewHolder(private val binding: CardVacancyListBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val radius = itemView.resources.getDimensionPixelSize(R.dimen.card_vacancy_list_margin_start_12dp)
    fun bind(data: Vacancy) {
        val cityName = data.area.name
        val vacancyTitleText = "${data.name}, $cityName"
        binding.vacancyTitle.text = vacancyTitleText
        binding.companyTitle.text = data.employer.name
        binding.vacancySalary.text = parseSalary(
            data.salary?.from,
            data.salary?.to,
            data.salary?.currency
        )

        Glide.with(itemView.context)
            .load(data.employer.logoUrls)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(radius))
            .into(binding.imageCompany)
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
