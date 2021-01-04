package com.example.flightstatsm2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_flight_detail.*

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FlightDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlightDetailFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mapViewModel: MapView
    private lateinit var InstGoogleMap: GoogleMap
    private lateinit var viewModel: FlightListViewModel
    private var selectIcao: String? = null
    private var selectTime: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)
        viewModel.getSelectedFlightNameLiveData().observe(this,{
            flight_name.text = it
        })


        val rootView = inflater.inflate(R.layout.fragment_flight_detail, container, false)
        //////////////////////////////////////////////////////////////
        viewModel = ViewModelProvider(requireActivity()).get(FlightListViewModel::class.java)
        mapViewModel = rootView.findViewById(R.id.mapView2) as MapView
        //////////////////////////////////////////////////////////////
        selectIcao = viewModel.getSelectedIcao() // get l'id du vol
        selectTime = viewModel.getSelectedTime() // Get la date
        //////////////////////////////////////////////////////////////
        mapViewModel.onCreate(savedInstanceState)
        mapViewModel.onResume()
        mapViewModel.getMapAsync(this)

        //Log.i("Test","rootView")
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlightDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FlightDetailFragment().apply {

            }
    }

    override fun onMapReady(p0: GoogleMap?) {
        InstGoogleMap = p0!!
        InstGoogleMap.setOnMapLoadedCallback(this)

        //Il rest l'appel API
        //searchFlightTrack(selectIcao!!, selectTime!!)
    }


    override fun onMapLoaded() {
        Log.i("Laflute","flute a bec")
    }
}