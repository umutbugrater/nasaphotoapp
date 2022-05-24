package com.umutbugrater.nasaphotoapp.service

import com.umutbugrater.nasaphotoapp.model.PhotoListDto
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NasaRoverService {

    private val api = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/mars-photos/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NasaRoverAPI::class.java)

    fun getRoverInfo(roverType: String, camera: String): Single<PhotoListDto> {

        if (camera == "") {
            return api.getRoverInfo(roverType)
        } else {
            return api.getRoverInfoWithCameraType(roverType, camera)
        }
    }

}