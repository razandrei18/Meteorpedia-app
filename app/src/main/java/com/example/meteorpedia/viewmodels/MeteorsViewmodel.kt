package com.example.meteorpedia.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meteorpedia.models.MeteorModel
import com.example.meteorpedia.models.Resource
import com.example.meteorpedia.repositories.MeteorsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class MeteorsViewmodel: KoinComponent, ViewModel() {

    companion object {
        const val TAG = "MeteorsViewmodel"
    }

    private val meteorsRepository by inject<MeteorsRepository>()
    private val disposable by inject<CompositeDisposable>()

    val getMeteorsResponse: LiveData<Resource<ArrayList<MeteorModel>>?>
        get() = _getMeteorsResponse
    private val _getMeteorsResponse = MutableLiveData<Resource<ArrayList<MeteorModel>>?>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData(false)

    fun getMeteorsData(){
        _isLoading.value = true
        disposable.add(
            meteorsRepository.getMeteors()
                .subscribeOn(Schedulers.newThread())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<MeteorModel>>(){
                    override fun onSuccess(response : ArrayList<MeteorModel>) {
                        _isLoading.value = false
                        _getMeteorsResponse.value = Resource.success(response)
                    }

                    override fun onError(e: Throwable) {
                        _isLoading.value = false
                        _getMeteorsResponse.value = Resource.error(e.message ?: "Error", null)
                        Log.e(TAG, e.message.toString())
                    }

                })
        )
    }

    fun resetMeteors(){
            _getMeteorsResponse.value = null
    }
}