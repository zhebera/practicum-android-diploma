package ru.practicum.android.diploma.util

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation

fun glide(
    context: Context,
    source: String,
    view: ImageView,
    transformation: BitmapTransformation
) {
    Glide.with(context)
        .load(source)
//        .placeholder() - добавить, когда появится res файл
        .transform(transformation)
        .into(view)

}

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics)
        .toInt()
}
