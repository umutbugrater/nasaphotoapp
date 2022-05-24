package com.umutbugrater.nasaphotoapp.service

import com.umutbugrater.nasaphotoapp.model.PhotoListDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaRoverAPI {


    @GET("api/v1/rovers/{roverType}/photos?sol=100&&api_key=qXscl0nKh2z1aWqeOslMcdQm16WbNuWxwdqQXs6G")
    fun getRoverInfoWithCameraType(
        @Path("roverType") roverType: String,
        @Query("camera") camera: String,
    ): Single<PhotoListDto>

    @GET("api/v1/rovers/{roverType}/photos?sol=100&&api_key=qXscl0nKh2z1aWqeOslMcdQm16WbNuWxwdqQXs6G")
    fun getRoverInfo(
        @Path("roverType") roverType: String
    ): Single<PhotoListDto>

}