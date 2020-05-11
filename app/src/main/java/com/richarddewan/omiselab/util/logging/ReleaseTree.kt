package com.richarddewan.omiselab.util.logging

import android.util.Log
import timber.log.Timber

class ReleaseTree: Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
       return !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO)

    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR){
            //log crash to analysis
        }
    }
}