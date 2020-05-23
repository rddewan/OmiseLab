package com.richarddewan.omiselab.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.di.scope.FragmentScope
import com.richarddewan.omiselab.ui.base.BaseFragment
import com.richarddewan.omiselab.ui.Charity.CharityViewModel
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
        ViewModelProvider(fragment,ViewModelProviderFactory(CharityViewModel::class){
            CharityViewModel(
                compositeDisposable, scheduleProvider, networkHelper, charityRepository
            )
        }).get(CharityViewModel::class.java)
}