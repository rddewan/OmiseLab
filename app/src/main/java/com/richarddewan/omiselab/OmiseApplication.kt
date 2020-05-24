package com.richarddewan.omiselab

import android.app.Application
import com.richarddewan.omiselab.di.component.ApplicationComponent
import com.richarddewan.omiselab.di.component.DaggerApplicationComponent
import com.richarddewan.omiselab.di.module.ApplicationModule
import com.richarddewan.omiselab.util.log.DebugTree
import com.richarddewan.omiselab.util.log.ReleaseTree
import timber.log.Timber

class OmiseApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        injectDependencies()

        if (BuildConfig.DEBUG) Timber.plant(DebugTree()) else Timber.plant(ReleaseTree())

    }

    private fun injectDependencies(){
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        applicationComponent.inject(this)
    }
}