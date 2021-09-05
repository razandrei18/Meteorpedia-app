package com.example.meteorpedia.models

import com.google.gson.annotations.SerializedName

data class MeteorModel(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("mass")
    val mass: String? = "",
    @SerializedName("fall")
    val fall: String? = "",
    @SerializedName("year")
    val fallenYear: String? = "",
    @SerializedName("geolocation")
    val geolocation: Geolocation?,
){
    data class Geolocation(
        @SerializedName("latitude")
        val latitude: String = "",
        @SerializedName("longitude")
        val longitude: String = ""
    )
}