package com.example.myapplication.Home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.AttractionData
import com.example.myapplication.EventsData
import com.example.myapplication.StylishApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    lateinit var events: List<EventsData>
    lateinit var attractions: List<AttractionData>
    var attractionsList = MutableLiveData<List<AttractionData>>()
    var hasApiBeenCalled: Boolean = false
    var eventsList = MutableLiveData<List<EventsData>>()

    suspend fun fetchData(lang: String) {
        viewModelScope.launch(Dispatchers.IO) {
            attractions = StylishApi.getAttractions(lang)
            events = StylishApi.getEvents(lang)
        }.join()
        attractionsList.postValue(attractions)
        eventsList.postValue(events)
    }
}