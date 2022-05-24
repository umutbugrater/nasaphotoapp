package com.umutbugrater.nasaphotoapp.model


import com.google.gson.annotations.SerializedName

data class CameraDto(
    @SerializedName("full_name")
    val fullName: String,
    val id: Int,
    val name: String,
    @SerializedName("rover_id")
    val roverİd: Int
)