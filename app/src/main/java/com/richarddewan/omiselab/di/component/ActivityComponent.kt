package com.richarddewan.omiselab.di.component

import com.richarddewan.omiselab.di.module.ActivityModule
import com.richarddewan.omiselab.di.scope.ActivityScope
import com.richarddewan.omiselab.ui.MainActivity
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}