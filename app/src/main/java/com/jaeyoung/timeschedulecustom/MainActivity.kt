package com.jaeyoung.timeschedulecustom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test = findViewById<TimeScheduleView>(R.id.timeSchedule)
        add_btn.setOnClickListener {
            test.addItem(Integer.parseInt(time_start.text.toString())*60+Integer.parseInt(min_start.text.toString()),
                Integer.parseInt(time_end.text.toString())*60+Integer.parseInt(min_end.text.toString()),title_et.text.toString())
        }
    }
}