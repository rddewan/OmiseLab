package com.richarddewan.omiselab.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.di.scope.FragmentScope
import com.richarddewan.omiselab.ui.base.BaseFragment
import com.richarddewan.omiselab.ui.dashboard.DashboardViewModel
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
    ): DashboardViewModel =
        ViewModelProvider(fragment,ViewModelProviderFactory(DashboardViewModel::class){
            DashboardViewModel(
                compositeDisposable, scheduleProvider, networkHelper, charityRepository
            )
        }).get(DashboardViewModel::class.java)
}