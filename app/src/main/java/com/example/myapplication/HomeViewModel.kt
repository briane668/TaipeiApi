package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    // 在這裡放置你的 API 相關邏輯


    // 這個函數用於在 ViewModel 中啟動協程
    fun fetchData(lang: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val attractionsList = StylishApi.getAttractions(lang)

                // 在這裡處理獲得的景點數據
                for (attraction in attractionsList) {
                    // 處理每個景點
                    println("Attraction Name: ${attraction.name}")
                }
            } catch (e: Exception) {
                // 處理錯誤
                e.printStackTrace()
            }
        }
    }
}