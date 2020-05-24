package com.richarddewan.omiselab.ui.donation


import androidx.lifecycle.MutableLiveData
import com.richarddewan.omiselab.data.remote.request.DonationRequest
import com.richarddewan.omiselab.data.remote.response.DonationResponse
import com.richarddewan.omiselab.data.repository.DonationRepository
import com.richarddewan.omiselab.ui.base.BaseViewModel
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class DonationViewModel(
    compositeDisposable: CompositeDisposable,
    scheduleProvider: ScheduleProvider,
    networkHelper: NetworkHelper,
    private val donationRepository: DonationRepository
    ) : BaseViewModel(compositeDisposable, scheduleProvider, networkHelper) {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val donationResponse: MutableLiveData<DonationResponse> = MutableLiveData()

    override fun onCreate() {

    }

    fun sendDonation(donationRequest: DonationRequest){
        isLoading.value = true
        if (checkNetworkConnectionWithMessage()){
            compositeDisposable.add(
                donationRepository.addDonation(donationRequest)
                    .subscribeOn(scheduleProvider.io())
                    .subscribe(
                        {
                            isLoading.postValue(false)
                            isSuccess.postValue(true)
                            donationResponse.postValue(it)

                        },
                        {
                            isLoading.postValue(false)
                            isSuccess.postValue(false)
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