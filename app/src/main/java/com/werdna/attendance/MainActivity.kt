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

    private var attendances : List<Attendance> = listOf<Attendance>() //Todo: Create method to import from database
    private var today : Attendance = Attendance()
    private var active : Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display     = findViewById<TextView>(R.id.txtDisplay)
        activeHours = findViewById<TextView>(R.id.txtActiveHours)
        status      = findViewById<TextView>(R.id.txtStatus)

        getToday()

        display.text = today.toString()
        activeHours.text = active.toString()

        /* Debug Section;*/
        //attendances.plus(today) //doesn't work?
        //attendances += today; //does work?
        status.text = attendances.toString()

    }

    private fun getToday()
    {
        //check if array is empty (Fresh month)
        //Todo: Import from database last date of the month before to get ID
        //Todo: make sure to have if statement when checking database. will only happy for first time use
        if(attendances.isEmpty()){ today.setId(1); attendances+=today; return; }

        //Checks if the date created today is already on the database
        if(!attendances.contains(today)) {
            if(attendances.isNotEmpty()){ today.setId(attendances.last().getId() + 1) }
            attendances+=today
        }else{
            //upon opening the app, if today's date is already in the array, set it to today
            today = attendances.last()
        }
    }

    fun setActiveTime(view : View)
    {
        active = 0.0

        //for each each attendance record in the array, parse the active time to a function
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
        //Todo: get booleon of halfpast element
        today.setStartTime()
        display.setText(today.toString())
    }

    fun setStartHalfPast(view : View)
    {
        //Todo: Remove
        attendances.last().setStartHalf(this.findViewById<Switch>(R.id.switchStartHalfPast).isChecked)
        display.text = attendances.last().toString()
    }

    fun setEndTime(view : View)
    {
        //Todo: get booleon of halfpast element
        today.setEndTime()
        display.text = today.toString()
    }

    fun setEndHalfPast(view : View)
    {
        //Todo: Remove
        attendances.last().setHalfPastEnd(this.findViewById<Switch>(R.id.switchEndHalfPast).isChecked)
        display.text = attendances.last().toString()
    }
}