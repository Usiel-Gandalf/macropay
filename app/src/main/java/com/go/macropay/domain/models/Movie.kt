package com.go.macropay.domain.models

data class Movie(
    val id: Int,
    val image: String = "",
    val title: String,
    val calification: String = "0",
)

data class MovieDetail(
    val image: String? = "No image",
    val title: String? = "No title",
    val duration: Int? = 0,
    val fechaEsterno: String? = "00/00/0000",
    val clasification: String? = "Non Clasification",
    val genere: String? = "Non Genere",
    val description: String? = "Not description",
)
