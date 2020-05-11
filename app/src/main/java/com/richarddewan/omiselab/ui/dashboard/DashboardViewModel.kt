package com.richarddewan.omiselab.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.richarddewan.omiselab.data.remote.NetworkService
import io.reactivex.disposables.CompositeDisposable

class DashboardViewModel(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}