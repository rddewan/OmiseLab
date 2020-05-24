package com.richarddewan.omiselab.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.data.repository.DonationRepository
import com.richarddewan.omiselab.di.scope.FragmentScope
import com.richarddewan.omiselab.ui.base.BaseFragment
import com.richarddewan.omiselab.ui.Charity.CharityViewModel
import com.richarddewan.omiselab.ui.Charity.detail.CharityDetailViewModel
import com.richarddewan.omiselab.ui.donation.DonationViewModel
import com.richarddewan.omiselab.ui.donation.success.DonationSuccessViewModel
import com.richarddewan.omiselab.util.ViewModelProviderFactory
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    @FragmentScope
    fun provideContext(): Context = fragment.requireContext()

    @Provides
    fun provideDashboardViewModel(
        compositeDisposable: CompositeDisposable,
        scheduleProvider: ScheduleProvider,
        networkHelper: NetworkHelper,
        charityRepository: CharityRepository
    ): CharityViewModel =
        ViewModelProvider(fragment, ViewModelProviderFactory(CharityViewModel::class) {
            CharityViewModel(
                compositeDisposable, scheduleProvider, networkHelper, charityRepository
            )
        }).get(CharityViewModel::class.java)

    @Provides
    fun provideCharityDetailViewModel(
        compositeDisposable: CompositeDisposable,
        scheduleProvider: ScheduleProvider,
        networkHelper: NetworkHelper
    ) :CharityDetailViewModel = ViewModelProvider(fragment, ViewModelProviderFactory(CharityDetailViewModel::class){
        CharityDetailViewModel(
            compositeDisposable, scheduleProvider, networkHelper
        )
    }).get(CharityDetailViewModel::class.java)

    @Provides
    fun provideDonationViewModel(
        compositeDisposable: CompositeDisposable,
        scheduleProvider: ScheduleProvider,
        networkHelper: NetworkHelper,
        donationRepository: DonationRepository
    ): DonationViewModel = ViewModelProvider(fragment,ViewModelProviderFactory(DonationViewModel::class){
        DonationViewModel(compositeDisposable, scheduleProvider, networkHelper, donationRepository)
    }).get(DonationViewModel::class.java)

    @Provides
    fun provideDonationSuccessViewModel(
        compositeDisposable: CompositeDisposable,
        scheduleProvider: ScheduleProvider,
        networkHelper: NetworkHelper
    ): DonationSuccessViewModel = ViewModelProvider(fragment,ViewModelProviderFactory(DonationSuccessViewModel::class){
        DonationSuccessViewModel(compositeDisposable, scheduleProvider, networkHelper)
    }).get(DonationSuccessViewModel::class.java)

}