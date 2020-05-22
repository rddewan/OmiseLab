package com.richarddewan.omiselab.di.module

import android.app.Application
import com.richarddewan.omiselab.BuildConfig
import com.richarddewan.omiselab.OmiseApplication
import com.richarddewan.omiselab.data.remote.NetworkService
import com.richarddewan.omiselab.data.remote.Networking
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.RxSchedulerProvide
import com.richarddewan.omiselab.util.rx.ScheduleProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: OmiseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

    @Provides
    fun provideScheduleProvider(): ScheduleProvider = RxSchedulerProvide()


    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService = Networking.create(
        BuildConfig.BASE_URL,
        application.cacheDir,
        10 * 1024 * 1024
    )
}