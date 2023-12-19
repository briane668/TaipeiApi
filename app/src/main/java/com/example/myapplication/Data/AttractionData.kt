package com.example.myapplication

import com.example.myapplication.Data.Category
import com.example.myapplication.Data.Image
import java.io.Serializable

data class AttractionData(
    val address: String,
    val category: List<Category>,
    val distric: String,
    val elong: Double,
    val email: String,
    val facebook: String,
    val fax: String,


    val id: Int,
    val images: List<Image>,
    val introduction: String,

    val modified: String,
    val months: String,
    val name: String,

    val nlat: Double,
    val official_site: String,
    val open_status: Int,
    val open_time: String,
    val remind: String,

    val staytime: String,

    val tel: String,
    val ticket: String,
    val url: String,
    val zipcode: String
): Serializable

data class AttractionResponse(
    val total :Int,
    val data: List<AttractionData>
)



