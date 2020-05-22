package com.richarddewan.omiselab.di.component

import com.richarddewan.omiselab.OmiseApplication
import com.richarddewan.omiselab.data.remote.NetworkService
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.di.module.ApplicationModule
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(omiseApplication: OmiseApplication)


    fun getNetworkHelper() : NetworkHelper

    fun getCompositeDisposable(): CompositeDisposable

    fun getScheduleProvider(): ScheduleProvider

    fun getNetworkService(): NetworkService

    fun getCharityRepository(): CharityRepository
}