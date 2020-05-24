package com.richarddewan.omiselab.ui.Charity.detail

import androidx.lifecycle.ViewModel
import com.richarddewan.omiselab.ui.base.BaseViewModel
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import io.reactivex.disposables.CompositeDisposable

class CharityDetailViewModel(
    compositeDisposable: CompositeDisposable,
    scheduleProvider: ScheduleProvider,
    networkHelper: NetworkHelper
) : BaseViewModel(compositeDisposable, scheduleProvider, networkHelper) {

    override fun onCreate() {

    }

}
