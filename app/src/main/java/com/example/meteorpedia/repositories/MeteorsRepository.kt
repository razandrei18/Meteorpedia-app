package com.example.meteorpedia.repositories

import com.example.meteorpedia.api.MeteorsApi
import com.example.meteorpedia.models.MeteorModel
import io.reactivex.Single

class MeteorsRepository (private val api: MeteorsApi) {

    fun getMeteors(): Single<ArrayList<MeteorModel>> {
        return api.getMeteors()
    }
}