package com.umutbugrater.nasaphotoapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umutbugrater.nasaphotoapp.model.PhotoListDto
import com.umutbugrater.nasaphotoapp.service.NasaRoverService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val nasaRoverService = NasaRoverService()
    private val disposable = CompositeDisposable()

    val nasaData = MutableLiveData<PhotoListDto>()
    val error = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun refreshData(roverType: String, cameraName: String) {

        isLoading.value = true
        disposable.add(
            nasaRoverService.getRoverInfo(roverType, cameraName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PhotoListDto>() {

                    override fun onSuccess(t: PhotoListDto) {
                        nasaData.value = t
                        error.value = false
                        isLoading.value = false
                        Log.i(TAG, "onSuccess: Get rover info request is successful")
                    }

                    override fun onError(e: Throwable) {
                        error.value = true
                        isLoading.value = false
                        Log.i(TAG, "onError: Get rover info request is failed!")
                    }

                })
        )
    }
}