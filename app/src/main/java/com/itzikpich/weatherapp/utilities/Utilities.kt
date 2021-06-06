package com.itzikpich.weatherapp.utilities

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension method to load imageView from url.
 */
fun ImageView.loadFromUrlToGlide(imageUrl: String?, onResourceReady: () -> Unit = {}) {
    GlideApp.with(this).asBitmap().load(imageUrl).listener(object : RequestListener<Bitmap> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            onResourceReady.invoke()
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onResourceReady.invoke()
            return false
        }
    }).into(this)
}

fun Double.toFahrenheit() = (this*9/5+32).toInt()

fun Long.toCustomMainDate() = SimpleDateFormat("E, d MMM HH:mm").format(this)
fun Long.toHMTime() = SimpleDateFormat("HH:mm").format(this*1000)
fun Long.toHourDayTime() = SimpleDateFormat("HH:mm\ndd/MM").format(this*1000)
fun Long.toMinutes() = (SimpleDateFormat("HH").format(this*1000)).toLong()*60 + (SimpleDateFormat("mm").format(this*1000)).toLong()

fun currentDateTime(): String = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale("he")).format(Date())