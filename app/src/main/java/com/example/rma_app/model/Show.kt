package com.example.rma_app.model

import java.io.Serializable

data class Show (
    val id: String = "",
    val name: String = "",
    val url: String = "",
    val language: String = "",
    val status: String = "",
    val premiered: String = "",
    val summary: String = "",
    val image: Image = Image("",""),
    val schedule: Schedule
) : Serializable{
    constructor() : this("","","","","","","", Image("", ""),Schedule("", listOf("")))
}

data class Image(
    val medium: String = "",
    val original: String = ""
) : Serializable

data class Schedule(
    val time: String = "",
    val days: List<String>
) : Serializable