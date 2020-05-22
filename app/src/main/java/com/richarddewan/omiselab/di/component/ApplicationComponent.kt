package com.richarddewan.omiselab.di.component

import com.richarddewan.omiselab.OmiseApplication
import com.richarddewan.omiselab.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(omiseApplication: OmiseApplication)
}