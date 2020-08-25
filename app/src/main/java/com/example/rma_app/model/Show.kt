package com.example.rma_app.model

data class Show(
    val id: String = "",
    val name: String = "",
    val url: String = "",
    val language: String = "",
    val status: String = "",
    val premiered: String = "",
    val summary: String = "",
    val image: Image = Image("",""),
    val schedule: Schedule

)

data class Image(
    val medium: String = "",
    val original: String = ""
)

data class Schedule(
    val time: String = "",
    val days: List<String>
)