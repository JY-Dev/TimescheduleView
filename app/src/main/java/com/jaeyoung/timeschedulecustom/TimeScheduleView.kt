package com.jaeyoung.timeschedulecustom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlin.math.*

class TimeScheduleView : ViewGroup {
    private var screenWidth = 0
    private var screenHeight = 0
    private val CENTER_ANGLE = 270f
    private val data = mutableListOf<Schedule>()

    constructor(context: Context) : super(context) {
        init()
        setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        setWillNotDraw(false)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    if (backgroundClickable(motionEvent.x, motionEvent.y)) {
                        val pX = getPositionX(motionEvent.x)
                        val pY = getPositionY(motionEvent.y)
                        val quad = quadrant(pX, pY)
                        val angle = getAngle(quad, abs(pX), abs(pY))
                        data.forEachIndexed { index, schedule ->
                            if (itemTouchCondition(
                                    angle,
                                    schedule.startAngle,
                                    schedule.endAngle,
                                    schedule.wholeAngle
                                )
                            ) {
                                Toast.makeText(context, schedule.title, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            true
        }
    }

    fun backgroundClickable(x: Float, y: Float): Boolean {
        val absX = abs(x)
        val absY = abs(y)
        val height = if (absY > screenWidth / 2) absY - (screenWidth / 2)
        else (screenWidth / 2) - absY
        val width = if (absX > screenWidth / 2) absX - (screenWidth / 2)
        else (screenWidth / 2) - absX
        return sqrt((width.toDouble().pow(2.0) + height.toDouble().pow(2.0))) < screenWidth / 2
    }

    fun itemTouchCondition(
        angle: Float,
        startAngle: Float,
        endAngle: Float,
        wholeAngle: Float
    ): Boolean {
        return (angle > startAngle && angle < endAngle) || (startAngle > endAngle && angle > startAngle || (angle >= 0 && angle < endAngle))
    }

    fun getAngle(quad: Int, width: Float, height: Float): Float {
        val angle = atan2(width.toDouble(), height.toDouble()) * 180 / PI
        return when (quad) {
            1 -> {
                angle.toFloat()
            }
            2 -> {
                360 - angle.toFloat()

            }
            3 -> {
                180 + angle.toFloat()
            }
            4 -> {
                180 - angle.toFloat()
            }
            else -> 0f
        }
    }

    fun quadrant(x: Float, y: Float): Int {
        return if (x > 0 && y > 0) 1
        else if (x < 0 && y > 0) 2
        else if (x < 0 && y < 0) 3
        else 4
    }

    fun getPositionX(p0: Float): Float {
        return if (p0 < screenWidth / 2) p0 - (screenWidth / 2)
        else p0 - (screenWidth / 2)
    }

    fun getPositionY(p0: Float): Float {
        return if (p0 < screenWidth / 2) -(p0 - (screenWidth / 2))
        else -(p0 - (screenWidth / 2))
    }

    fun getTimeAngle(hour: Int, min: Int): Float {
        return (hour * 60 + min) / 1440f * 360f
    }

    fun getTimeAngle(time: Int): Float {
        return (time) / 1440f * 360f
    }

    fun addItem(startTime: Int, endTime: Int, title: String) {
        val startAngle = getTimeAngle(startTime)
        val endAngle = getTimeAngle(endTime)
        val wholeAngle =
            if (startAngle > endAngle) 360 - startAngle + endAngle else endAngle - startAngle
        var checkFlag = true
        data.forEach {
            checkFlag = !(addOverlapCheck(it.startAngle,it.endAngle,startAngle,endAngle))
         /*   if ((startAngle > it.startAngle && startAngle < it.endAngle) || (endAngle > it.startAngle && endAngle < it.endAngle)) {
                checkFlag = false
            }*/
        }
        if (checkFlag) data.add(Schedule(startAngle, endAngle, wholeAngle, title))
        else Toast.makeText(context, "안됨", Toast.LENGTH_SHORT).show()
        invalidate()
    }

    fun addOverlapCheck(preStart: Float, preEnd: Float, start: Float, end: Float): Boolean {
        return (start>preStart && start < preEnd) || (end > preStart && end < preEnd)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        screenHeight = screenWidth
        setMeasuredDimension(screenWidth, screenHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        println("Invalidate")
        val pnt = Paint();
        val pntBack = Paint();
        pntBack.apply {
            strokeWidth = 6f
            color = Color.parseColor("#000000")
            style = Paint.Style.FILL_AND_STROKE
        }

        pnt.apply {
            strokeWidth = 6f
            color = Color.parseColor("#FF0000")
            style = Paint.Style.FILL
        }

        val rect = RectF()
        rect.set(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat())
        canvas?.drawArc(rect, CENTER_ANGLE , 360f, true, pntBack)
        data.forEach {
            canvas?.drawArc(rect, CENTER_ANGLE + it.startAngle, it.wholeAngle, true, pnt)
        }

        //pnt.setColor(Color.parseColor("#00FF00"));
        // canvas?.drawArc(rect, CENTER_ANGLE + 180f, 180f, true, pnt)
    }

    data class Schedule(
        val startAngle: Float,
        val endAngle: Float,
        val wholeAngle: Float,
        val title: String
    )
}