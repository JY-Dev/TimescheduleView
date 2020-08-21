package com.jaeyoung.timeschedulecustom

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout

class TimeBackView : LinearLayout {
    private var screenWidth = 0
    private var screenHeight = 0
    constructor(context: Context) : super(context
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun init(){
        this.setBackgroundResource(R.drawable.back)
        addView(TimeScheduleView(context))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        screenHeight = screenWidth
        setMeasuredDimension(screenWidth,screenHeight)
        println("test01="+screenWidth)
        gravity = Gravity.CENTER_VERTICAL

    }
}