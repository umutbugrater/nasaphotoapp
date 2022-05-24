package com.umutbugrater.nasaphotoapp.service

import com.umutbugrater.nasaphotoapp.model.PhotoDto

interface RecyclerViewClick {

    fun openWindow(photoDtoClick: PhotoDto)
}