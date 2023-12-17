package com.example.myapplication

data class AttractionData(
    val address: String,
    val category: List<Category>,
    val distric: String,
    val elong: Double,
    val email: String,
    val facebook: String,
    val fax: String,
    val files: List<Any>,
    val friendly: List<Any>,
    val id: Int,
    val images: List<Image>,
    val introduction: String,
    val links: List<Any>,
    val modified: String,
    val months: String,
    val name: String,
    val name_zh: Any?,
    val nlat: Double,
    val official_site: String,
    val open_status: Int,
    val open_time: String,
    val remind: String,
    val service: List<Any>,
    val staytime: String,
    val target: List<Any>,
    val tel: String,
    val ticket: String,
    val url: String,
    val zipcode: String
)

data class AttractionResponse(
    val total :Int,
    val data: List<AttractionData>
)



