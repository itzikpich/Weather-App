package com.itzikpich.weatherapp.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.itzikpich.weatherapp.models.CitySunrizeSunsetData
import com.itzikpich.weatherapp.utilities.toHMTime
import com.itzikpich.weatherapp.utilities.toMinutes
import kotlin.properties.Delegates

private const val TAG = "WeatherGraph"

class WeatherGraph @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var citySunrizeSunsetData: CitySunrizeSunsetData? by Delegates.observable(null) { property, oldValue, newValue ->
        val divider = this.width / 1440f
        val rize = newValue?.sunrizeTime?.toMinutes()?.times(divider)
        val set = newValue?.sunsetTime?.toMinutes()?.times(divider)
        if (set == null || rize == null) return@observable
        rectF = RectF(rize, 0f, set, this.height.toFloat())
        rectFStart = RectF(0f, 0f, rize, this.height.toFloat())
        rectFEnd = RectF(set, 0f, this.width.toFloat(), this.height.toFloat())
        invalidate()
//        RectF(0f, 30f, 500f, 200f)
    }

    val path = Path()
    val drawPath = Path()
    var rectF: RectF? = null
    var rectFStart: RectF? = null
    var rectFEnd: RectF? = null

    private val drawPaint: Paint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 2F
    }

    private val linePaint: Paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 2F
    }

    init {
        Log.d(TAG, "init ")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        val divider = this.width.toFloat() / 1440
//        val rize = 1622687562L.toMinutes() * divider
//        val set = 1622738650L.toMinutes() * divider
//        newValue?.lengthOfDay?.toMinutes()
//        val rectF = RectF(rize, 0f, set, this.height.toFloat())
        super.onDraw(canvas)
        canvas.drawLine(0f, this.height/2f, this.width.toFloat(), this.height/2f, linePaint)
        rectF?.let { canvas.drawArc(it, 180f, 180f, false, drawPaint) }
        rectFStart?.let { canvas.drawArc(it, 0f, 180f, false, drawPaint) }
        rectFEnd?.let { canvas.drawArc(it, 0f, 180f, false, drawPaint) }
    }


}