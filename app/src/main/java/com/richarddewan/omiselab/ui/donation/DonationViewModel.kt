package com.richarddewan.omiselab.ui.donation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.richarddewan.omiselab.data.remote.NetworkService
import io.reactivex.disposables.CompositeDisposable

class DonationViewModel(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}