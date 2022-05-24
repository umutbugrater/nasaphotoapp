package com.umutbugrater.nasaphotoapp.model


import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val camera: CameraDto,
    @SerializedName("earth_date")
    val earthDate: String,
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    val rover: RoverDto,
    val sol: Int
)