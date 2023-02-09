package com.werdna.attendance

import java.time.LocalDateTime

class Attendance {
    private var id              : Int = 0 //Idea could be combination of the date's day, month, year
    private var dateToday       : LocalDateTime //Used to get date
    private var timeStart       : String = "00:00"
    private var timeEnd         : String = "00:00"
    private var isWorking       : Boolean
    private var activeTime      : Double = 0.0
    private var isHalfPastStart : Boolean = false;
    private var isHalfPastEnd   : Boolean = false;
    //Todo: to add break time to be trackable. and deducted from active time

    constructor(idInsert : Int, timeStartInsert : String, timeEndInsert : String,
                dateTimeInsert : LocalDateTime, isWorkingInsert : Boolean,
                isHalfPastStartInsert : Boolean, isHalfPastEndInsert : Boolean)
    {
        this.id         = idInsert
        this.timeStart  = timeStartInsert
        this.timeEnd    = timeEndInsert
        this.dateToday  = dateTimeInsert
        this.isWorking  = isWorkingInsert
        this.isHalfPastStart = isHalfPastStartInsert
        this.isHalfPastEnd = isHalfPastEndInsert

        setActiveTime()
    }

    constructor()
    {
        this.isWorking  = false
        this.dateToday = LocalDateTime.now();
    }

    override fun toString() : String
    {
        var display = "";
        var dayOfWeek : String = dateToday.dayOfWeek.toString()

        //Goes through each possible date, and the impossible 32, to set the text for something different
        display += when(this.dateToday.dayOfMonth)
        {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            4 -> "4th"
            5 -> "5th"
            6 -> "6th"
            7 -> "7th"
            8 -> "8th"
            9 -> "9th"
            10 -> "10th"
            11 -> "11th"
            12 -> "12th"
            13 -> "13th"
            14 -> "14th"
            15 -> "15th"
            16 -> "16th"
            17 -> "17th"
            18 -> "18th"
            19 -> "19th"
            20 -> "20th"
            21 -> "21st"
            22 -> "22nd"
            23 -> "23rd"
            24 -> "24th"
            25 -> "25th"
            26 -> "26th"
            27 -> "27th"
            28 -> "28th"
            29 -> "29th"
            30 -> "30th"
            31 -> "31st"
            32 -> "32nd"
            else -> "[!] ERROR DAY NOT FOUND"
        }

        display += " "
        display += dayOfWeek.substring(0,1)
        display+= dayOfWeek.substring(1).lowercase()

        if(isWorking)
        {
            display += " "
            display += timeStart
            display += " - "
            display += timeEnd
        }else{  display += " Not Working"  }

        return display
    }

    fun setStartTime()
    {
        this.isWorking  = true
        //Todo: Consider using global variable today, note: could cause issues if user works night shift when the date goes over
        val datetmp : LocalDateTime = LocalDateTime.now() //creates local variable time to get today's time
        this.timeStart = datetmp.hour.toString()
        if(isHalfPastStart) {   this.timeStart += ":30" }
        else                {   this.timeStart += ":00" }
    }

    fun setStartHalf(isHalfPast : Boolean)
    {
        this.isHalfPastStart = isHalfPast
    }

    fun setEndTime()
    {
        val datetmp : LocalDateTime = LocalDateTime.now()
        this.timeEnd = datetmp.hour.toString()
        if(isHalfPastEnd)   {   this.timeEnd += ":30" }
        else                {   this.timeEnd += ":00" }

        setActiveTime()
    }

    private fun setActiveTime()
    {
        //Converts both to double for easier maths, stored as text for easy display
        val start : Double = this.timeStart.substring(0, this.timeStart.length - 3).toDouble()
        val end : Double = this.timeEnd.substring(0, this.timeStart.length - 3).toDouble()


        this.activeTime = 0.0

        //Goes through each combination of the start and finish as to not go over
        if((this.isHalfPastStart && !this.isHalfPastEnd)
            ||
            (!this.isHalfPastStart && this.isHalfPastEnd))
        {
            this.activeTime += 0.5
        }
        if(this.isHalfPastStart && this.isHalfPastEnd)
        {
            this.activeTime+=1
        }

        this.activeTime += end - start
    }

    fun setId( idInsert : Int)
    {
        this.id = idInsert
    }

    fun setHalfPastEnd(isHalfPast : Boolean)
    {
        this.isHalfPastEnd = isHalfPast
    }

    fun getActiveTime() : Double
    {
        return this.activeTime
    }

    fun getDay() : Int
    {
        return this.dateToday.dayOfMonth
    }

    fun getMonth() : Int
    {
        return this.dateToday.dayOfYear
    }

    fun getId() : Int
    {
        return this.id
    }
}