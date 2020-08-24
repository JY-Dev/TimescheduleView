package com.jaeyoung.timeschedulecustom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout

class TimeBackMainView : LinearLayout {
    private var screenWidth = 0
    private var screenHeight = 0
    constructor(context: Context) : super(context
    ) {
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
    ){ setWillNotDraw(false)}

    private fun init(){
        setBackgroundResource(R.drawable.circle_clock)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        screenHeight = screenWidth
        val padding = 20.dp
        setPadding(padding,padding,padding,padding)
        setMeasuredDimension(screenWidth,screenHeight)

    }
}