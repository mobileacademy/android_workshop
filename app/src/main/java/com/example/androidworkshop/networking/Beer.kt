package com.example.androidworkshop.networking

import com.google.gson.annotations.SerializedName

data class Beer(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String)