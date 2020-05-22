package com.richarddewan.omiselab.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.richarddewan.omiselab.data.remote.NetworkService
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.ui.base.BaseViewModel
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import io.reactivex.disposables.CompositeDisposable

class DashboardViewModel(
    compositeDisposable: CompositeDisposable,
    scheduleProvider: ScheduleProvider,
    networkHelper: NetworkHelper,
    private val charityRepository: CharityRepository

) : BaseViewModel(compositeDisposable,scheduleProvider,networkHelper) {

    override fun onCreate() {

    }

}