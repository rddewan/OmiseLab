package com.richarddewan.omiselab.di.module

import android.content.Context
import com.richarddewan.omiselab.di.scope.ActivityScope
import com.richarddewan.omiselab.ui.base.BaseActivity
import dagger.Module
import dagger.Provides


@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    @ActivityScope
    fun provideContext(): Context = activity


}