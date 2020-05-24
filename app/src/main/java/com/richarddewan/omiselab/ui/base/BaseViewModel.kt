package com.richarddewan.omiselab.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel(
    protected val compositeDisposable: CompositeDisposable,
    protected val scheduleProvider: ScheduleProvider,
    protected val networkHelper: NetworkHelper
) : ViewModel() {

    val messageString: MutableLiveData<String> = MutableLiveData()
    val messageStringId: MutableLiveData<Int> = MutableLiveData()

    abstract fun onCreate()

    protected fun checkNetworkConnection(): Boolean = networkHelper.isNetworkConnected()

    protected fun checkNetworkConnectionWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) true
        else {
            messageStringId.postValue(R.string.network_connection_error)
            false
        }

    /*
    handle network error
     */
    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {

                    HttpsURLConnection.HTTP_NOT_FOUND ->
                        messageStringId.postValue(R.string.network_page_not_found)

                    HttpsURLConnection.HTTP_UNAUTHORIZED ->
                        messageStringId.postValue(R.string.server_connection_error)

                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageStringId.postValue(R.string.network_internal_error)

                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringId.postValue(R.string.network_server_not_available)

                    -1 -> messageStringId.postValue(R.string.network_default_error)

                    0 -> messageStringId.postValue(R.string.server_connection_error)

                    else -> messageString.postValue(message)
                }
            }
        }



    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}