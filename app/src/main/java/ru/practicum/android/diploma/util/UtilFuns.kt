package ru.practicum.android.diploma.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun loadImageIntoView(
    context: Context,
    source: String,
    view: ImageView,
    transformation: BitmapTransformation? = null
) {
    Glide.with(context)
        .load(source)
        .placeholder(R.drawable.placeholder)
        .transform(transformation)
        .into(view)

}

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
        .toInt()
}

fun <T> debounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}

fun Int.getNumberString(context: Context): String {
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(Locale("ru"))
    val localizedResources: Resources = context.createConfigurationContext(configuration).resources
    return localizedResources.getQuantityString(
        R.plurals.founded_vacancies,
        this,
        this
    )
}

private fun formatSalary(value: Int): String {
    val decimalFormat = DecimalFormat("###,###,###,###,###", DecimalFormatSymbols(Locale.ENGLISH))
    return decimalFormat.format(value).replace(",", " ")
}

fun parseSalary(
    from: Int?,
    to: Int?,
    currency: String?,
    itemView: View
): String {
    val usedCurrency = currency ?: ""
    return if (from == null || to == null) {
        if (from != null) {
            itemView.resources.getString(R.string.salary_from, formatSalary(from), getCurrencyIcon(usedCurrency))
        } else if (to != null) {
            itemView.resources.getString(R.string.salary_to, formatSalary(to), getCurrencyIcon(usedCurrency))
        } else {
            itemView.resources.getString(R.string.salary_not_specified)
        }
    } else {
        itemView.resources.getString(
            R.string.salary_from_to,
            formatSalary(from),
            formatSalary(to),
            getCurrencyIcon(usedCurrency)
        )
    }
}

fun getCurrencyIcon(currency: String?): String? {
    return when (currency) {
        "RUR", "RUB" -> "\u20BD"
        "BYR" -> "Br"
        "USD" -> "\u0024"
        "EUR" -> "\u20AC"
        "KZT" -> "\u20B8"
        "UAH" -> "\u20B4"
        "AZN" -> "\u20BC"
        "GEL" -> "\u20BE"
        else -> currency
    }
}
