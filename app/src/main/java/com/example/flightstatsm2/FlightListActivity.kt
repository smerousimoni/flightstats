package com.example.flightstatsm2

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_flight_list.*

class FlightListActivity : AppCompatActivity(), FlightListRecyclerAdapter.OnItemClickListener {

    private lateinit var viewModel: FlightListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)


        viewModel = ViewModelProvider(this).get(FlightListViewModel::class.java)
        viewModel.search(
            intent.getStringExtra("icao")!!,
            intent.getBooleanExtra("isArrival", false),
            intent.getLongExtra("begin", 0),
            intent.getLongExtra("end", 0)
        )

        viewModel.flightListLiveData.observe(this, Observer {
            if (it == null || it.isEmpty()) {
                //DISPLAY ERROR
            } else {
                val adapter = FlightListRecyclerAdapter()
                adapter.flightList = it
                adapter.onItemClickListener = this
                recyclerView.adapter = adapter
                recyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
        })

        viewModel.isLoadingLiveData.observe(this, Observer {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })

    }

    override fun onItemClicked(flightName: String) {
        //DO SOMETHING WHEN CLICKING ON THE FLIGHT NAME
        Log.d("ViewClicked", flightName)
    }
}