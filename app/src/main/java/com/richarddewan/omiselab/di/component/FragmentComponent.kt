package com.richarddewan.omiselab.di.component

import com.richarddewan.omiselab.di.module.FragmentModule
import com.richarddewan.omiselab.di.scope.FragmentScope
import com.richarddewan.omiselab.ui.dashboard.DashboardFragment
import com.richarddewan.omiselab.ui.home.HomeFragment
import com.richarddewan.omiselab.ui.notifications.NotificationsFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: DashboardFragment)

    fun inject(fragment: NotificationsFragment)
}
