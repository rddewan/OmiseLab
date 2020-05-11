package com.richarddewan.omiselab.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richarddewan.omiselab.data.remote.NetworkService
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }


}