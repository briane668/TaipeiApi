package com.example.myapplication.Data

import java.io.Serializable

data class Image(
    val ext: String,
    val src: String,
    val subject: String
): Serializable