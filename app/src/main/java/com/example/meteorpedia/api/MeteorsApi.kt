package com.example.meteorpedia.api

import com.example.meteorpedia.models.MeteorModel
import io.reactivex.Single
import retrofit2.http.GET

interface MeteorsApi {
    @GET("resource/gh4g-9sfh.json")
    fun getMeteors() : Single<ArrayList<MeteorModel>>
}