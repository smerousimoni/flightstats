package com.example.flightstatsm2

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val DATE_FORMAT = "dd MMM yyy"

    val fromCalendar = Calendar.getInstance()
    val toCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val airportNamesList = ArrayList<String>()
        val airportList = Utils.generateAirportList()
        for (airport in airportList) {
            airportNamesList.add(airport.getFormattedName())
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            airportNamesList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner_airport.adapter = adapter

        displaySelectedDate(fromDate, fromCalendar)
        displaySelectedDate(toDate, toCalendar)

        fromDate.setOnClickListener({ showDatePicker(fromDate, fromCalendar) })
        toDate.setOnClickListener({ showDatePicker(toDate, toCalendar) })
    }

    private fun showDatePicker(textView: TextView, calendar: Calendar) {
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(year,monthOfYear,dayOfMonth)
                displaySelectedDate(textView, calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpd.show()
    }

    private fun displaySelectedDate(textView:  , calendar: Calendar) {
        textView.text = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(calendar.time)
    }
}