package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel: ViewModel() {

    // 在這裡放置你的 API 相關邏輯


    lateinit var events: List<EventsData>
    lateinit var attractions: List<AttractionData>
    var attractionsList = MutableLiveData<List<AttractionData>>()


     var eventsList = MutableLiveData<List<EventsData>>()

    // 這個函數用於在 ViewModel 中啟動協程
    suspend fun fetchData(lang: String) {
        viewModelScope.launch(Dispatchers.IO) {


            attractions = StylishApi.getAttractions(lang)

            events = StylishApi.getEvents(lang)
        }.join()
        attractionsList.postValue(attractions)
        eventsList.postValue(events)

        for (attraction in attractions) {
            // 處理每個景點
            println("Attraction Name: ${attraction.name}")
        }


    }
}