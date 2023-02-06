package com.werdna.attendance

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var display : TextView
    private lateinit var activeHours : TextView
    private lateinit var status : TextView

    private var attendances : List<Attendance> = listOf<Attendance>()
    private var today : Attendance = Attendance()
    private var active : Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display     = findViewById<TextView>(R.id.txtDisplay)
        activeHours = findViewById<TextView>(R.id.txtActiveHours)
        status      = findViewById<TextView>(R.id.txtStatus)

        display.text = attendances[0].toString()
        activeHours.text = active.toString()

        getToday()
    }

    private fun getToday()
    {
        if(attendances.isNotEmpty()){ today.setId(attendances.last().getId()) }

        if(!attendances.contains(today)) {
            if(attendances.isNotEmpty()){ today.setId(attendances.last().getId() + 1) }
            attendances.plus(today);
        }
    }

    fun setActiveTime(view : View)
    {
        active = 0.0
        attendances.forEach{
            activeTime(it.getActiveTime())
        }
        activeHours.text = active.toString()
    }

    fun activeTime(time: Double)
    {
        active += time
    }


    fun setStartTime(view : View)
    {
        attendances.last().setStartTime()
        display.setText(attendances.last().toString())
    }

    fun setStartHalfPast(view : View)
    {
        attendances.last().setStartHalf(this.findViewById<Switch>(R.id.switchStartHalfPast).isChecked)
        display.text = attendances.last().toString()
    }

    fun setEndTime(view : View)
    {
        attendances.last().setEndTime()
        display.text = attendances.last().toString()
    }

    fun setEndHalfPast(view : View)
    {
        attendances.last().setHalfPastEnd(this.findViewById<Switch>(R.id.switchEndHalfPast).isChecked)
        display.text = attendances.last().toString()
    }
}