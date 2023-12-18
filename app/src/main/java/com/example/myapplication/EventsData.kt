package com.example.myapplication

data class EventsData(
    val begin: Any?,
    val description: String,
    val end: Any?,
    val files: List<Any?>,
    val id: Int,
    val links: List<Link>,
    val modified: String,
    val posted: String,
    val title: String,
    val url: String
)

data class EventsResponse(
    val total :Int,
    val data: List<EventsData>
)