package com.abyte.androiddemos.view

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.abyte.androiddemos.R

/**
 * 含有百分比的圆角进度条
 * 自定义View，需要添加@JvmOverloads，一般来说，我们需要重载构造函数
 */
class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bgColor = Color.WHITE

    private var progressColor = Color.RED

    private var progressTextColor = Color.BLACK

    private var isShowProgressText = false

    private var percent: Float = 0f
        set(value) {
            val tempPercent =
                when {
                    value < 0f -> 0f
                    value > 1f -> 1f
                    else -> value
                }
            if (tempPercent != field) {
                field = tempPercent
                invalidate()
            }
        }

    private val backgroundPaint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            color = bgColor
        }
    }

    private val progressPaint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            color = progressColor
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
    }

    private val progressTextPaint by lazy {
        TextPaint().apply {
            style = Paint.Style.FILL
            color = progressTextColor
        }
    }

    private val backgroundRectF = RectF()
    private val progressRectF = RectF()

    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.CircularProgressView).apply {
                bgColor =
                    getColor(R.styleable.CircularProgressView_cp_background_color, Color.WHITE)

                progressColor =
                    getColor(R.styleable.CircularProgressView_cp_progress_color, Color.RED)

                progressTextColor =
                    getColor(R.styleable.CircularProgressView_cp_progress_text_color, Color.BLACK)

                percent =
                    getFloat(R.styleable.CircularProgressView_cp_percent, 0f)

                isShowProgressText =
                    getBoolean(R.styleable.CircularProgressView_cp_is_show_progress_text, false)

                recycle()
            }
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val halfHeight = height / NUM_2
        // 将回执操作保存到新的涂层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        val saveCount = canvas!!.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        // Draw background.
        backgroundRectF.left = paddingStart.toFloat()
        backgroundRectF.top = paddingTop.toFloat()
        backgroundRectF.right = width - paddingEnd.toFloat()
        backgroundRectF.bottom = height - paddingBottom.toFloat()
        canvas.drawRoundRect(backgroundRectF, halfHeight, halfHeight, backgroundPaint)

        // Draw progress.
        progressRectF.left = -backgroundRectF.width() + percent * width
        progressRectF.top = backgroundRectF.top
        progressRectF.right = progressRectF.left + backgroundRectF.width()
        progressRectF.bottom = backgroundRectF.bottom
        canvas.drawRoundRect(progressRectF, halfHeight, halfHeight, progressPaint)

        canvas.restoreToCount(saveCount)

        if (isShowProgressText && percent >= TIME_APPEAR_PROGRESS_TEXT) {
            progressTextPaint.run {
                textSize = halfHeight
                val progressText = (percent * PERCENT_100).toInt().toString() + PERCENT_MARK
                canvas.drawText(
                    progressText,
                    percent * width - progressTextPaint.measureText(progressText) - height / NUM_5,
                    halfHeight - (fontMetrics.descent + fontMetrics.ascent) / NUM_2,
                    progressTextPaint
                )
            }
        }
    }


    fun startAnimator(
        timeInterpolator: TimeInterpolator? = LinearInterpolator(),
        duration: Long
    ) =
        with(ObjectAnimator.ofFloat(this, PROPERTY_PERCENT, 0f, percent)) {
            interpolator = timeInterpolator
            this.duration = duration
            start()
        }


    companion object {
        const val TIME_APPEAR_PROGRESS_TEXT = 0.1f
        const val PERCENT_100 = 100
        const val NUM_5 = 5f
        const val NUM_2 = 2f
        const val PERCENT_MARK = "%"
        const val PROPERTY_PERCENT = "percent"
    }


}