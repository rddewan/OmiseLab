package com.richarddewan.omiselab

import android.app.Application
import com.richarddewan.omiselab.util.log.DebugTree
import com.richarddewan.omiselab.util.log.ReleaseTree
import timber.log.Timber

class OmiseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(DebugTree())
        }
        else {
            Timber.plant(ReleaseTree())
        }

    }
}