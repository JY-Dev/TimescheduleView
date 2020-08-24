package com.jaeyoung.timeschedulecustom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val color = mutableListOf("#00ff00","#202020","#0000ff","#ff0000","#5e5e5e","#ee0000","#505050","#ff2020","#ff3030","#ffee22")
        val ramdom = Random
        val test = findViewById<TimeScheduleView>(R.id.timeSchedule)
        add_btn.setOnClickListener {
            val num = ramdom.nextInt(0,9)
            test.addItem(Integer.parseInt(time_start.text.toString())*60+Integer.parseInt(min_start.text.toString()),
                Integer.parseInt(time_end.text.toString())*60+Integer.parseInt(min_end.text.toString()),title_et.text.toString(),
                color[num]
            )
        }
    }
}