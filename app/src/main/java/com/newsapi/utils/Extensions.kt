package com.newsapi.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.newsapi.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).apply(
        RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    ).error(R.mipmap.ic_launcher).into(this)
}


@SuppressLint("SimpleDateFormat")
fun String?.toDate(format: String): Date? {
    if (this.isNullOrEmpty()) {
        return Date()
    }
    return SimpleDateFormat(format).parse(this)
}

fun Date?.toString(format: String): String {
    if (this == null) return ""
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(this)
}

@SuppressLint("SimpleDateFormat")
fun getTenDaysAgo(): String {
    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -10)
    val tenDaysAgo: Date = calendar.time
    val dateFormat = SimpleDateFormat(DateFormat.UI_FORMAT)
    return dateFormat.format(tenDaysAgo)

}

fun Context.showDatePicker(selectedDate: String, clickListener: (String) -> Unit = {}) {
    val calendar = Calendar.getInstance()

    selectedDate.toDate(DateFormat.UI_FORMAT)?.let {
        calendar.time = it
    }

    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        this, { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat(DateFormat.UI_FORMAT, Locale.getDefault())
            clickListener.invoke(sdf.format(selectedCalendar.time))
        }, currentYear, currentMonth, currentDay
    )

    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    datePickerDialog.show()
}
