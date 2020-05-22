package com.richarddewan.omiselab.di.module

import android.app.Application
import com.richarddewan.omiselab.OmiseApplication
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val omiseApplication: OmiseApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = omiseApplication

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}