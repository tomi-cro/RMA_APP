package com.example.rma_app.listeners

import com.example.rma_app.model.Show

interface ReadDataListener {
    fun readData(list: ArrayList<Show>)
}