package com.example.flightstatsm2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by sergio on 19/11/2020
 * All rights reserved GoodBarber
 */
class FlightListViewModel : ViewModel(), RequestsManager.RequestListener {


    val flightListLiveData: MutableLiveData<List<FlightModel>> = MutableLiveData()
    val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val selectedFlightNameLiveData: MutableLiveData<String> = MutableLiveData()
    private val selectedIcaoLiveData: MutableLiveData<String> = MutableLiveData() //A CHANGER
    private val selectedTimeLiveData: MutableLiveData<Long> = MutableLiveData() //A CHANGE

    fun getSelectedFlightNameLiveData(): LiveData<String> {
        return selectedFlightNameLiveData
    }


    fun search(icao: String, isArrival: Boolean, begin: Long, end: Long) {

        val searchDataModel = SearchDataModel(
            isArrival,
            icao,
            begin,
            end
        )
        val baseUrl: String = if (isArrival) {
            "https://opensky-network.org/api/flights/arrival"
        } else {
            "https://opensky-network.org/api/flights/departure"
        }

        viewModelScope.launch {
            //start loading
            isLoadingLiveData.value = true
            val result = withContext(Dispatchers.IO) {
                RequestsManager.getSuspended(baseUrl, getRequestParams(searchDataModel))
            }
            //end loading
            isLoadingLiveData.value = false
            if (result == null) {
                Log.e("Request", "problem")

            } else {
                val flightList = Utils.getFlightListFromString(result)
                Log.d("models list", flightList.toString())
                flightListLiveData.value = flightList
            }

        }
        // SearchFlightsAsyncTask(this).execute(searchDataModel)
    }

    private fun getRequestParams(searchModel: SearchDataModel?): Map<String, String>? {
        val params = HashMap<String, String>()
        if (searchModel != null) {
            params["end"] = searchModel.end.toString()
            params["begin"] = searchModel.begin.toString()
            params["airport"] = searchModel.icao


        }
        return params
    }

    override fun onRequestSuccess(result: String?) {
        println(result)
    }

    override fun onRequestFailed() {
        TODO("Not yet implemented")
    }

    fun getSelectedIcao(): String {
        return selectedIcaoLiveData.value!!
    }
    fun getSelectedTime(): Long {
        return selectedTimeLiveData.value!!
    }

    fun updateSelectedIcao(icao: String){
        selectedIcaoLiveData.value = icao
    }

    fun updateSelectedTime(time: Long){
        selectedTimeLiveData.value = time
    }

    fun updateSelectedFlightName(flightName: String) {
        selectedFlightNameLiveData.value = flightName
    }
}