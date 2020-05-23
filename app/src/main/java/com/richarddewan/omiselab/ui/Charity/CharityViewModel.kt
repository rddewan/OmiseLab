package com.richarddewan.omiselab.ui.Charity

import androidx.lifecycle.MutableLiveData
import com.richarddewan.omiselab.data.remote.response.CharityResponseList
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.ui.base.BaseViewModel
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class CharityViewModel(
    compositeDisposable: CompositeDisposable,
    scheduleProvider: ScheduleProvider,
    networkHelper: NetworkHelper,
    private val charityRepository: CharityRepository

) : BaseViewModel(compositeDisposable,scheduleProvider,networkHelper) {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val charirtyList: MutableLiveData<List<CharityResponseList>> = MutableLiveData()


    override fun onCreate() {
        getCharityList()
        Timber.d("onCreate")

    }

    fun getCharityList(){
        isLoading.value = true
        if (checkNetworkConnectionWithMessage()){
            compositeDisposable.add(
                charityRepository.getCharity()
                    .subscribeOn(scheduleProvider.io())
                    .subscribe(
                        {
                            Timber.d(it.data.toString())
                            charirtyList.postValue(it.data)
                            isLoading.postValue(false)
                        },
                        {
                            isLoading.postValue(false)
                            handleNetworkError(it)
                            Timber.e(it)
                        }
                    )
            )
        }
        else {
            isLoading.value = false
        }
    }

}