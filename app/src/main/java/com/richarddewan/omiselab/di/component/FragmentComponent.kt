package com.richarddewan.omiselab.di.component

import com.richarddewan.omiselab.di.module.FragmentModule
import com.richarddewan.omiselab.di.scope.FragmentScope
import com.richarddewan.omiselab.ui.Charity.CharityFragment
import com.richarddewan.omiselab.ui.Charity.detail.CharityDetailFragment
import com.richarddewan.omiselab.ui.donation.DonationFragment
import com.richarddewan.omiselab.ui.notifications.NotificationsFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: DonationFragment)

    fun inject(fragment: CharityFragment)

    fun inject(fragment: NotificationsFragment)

    fun inject(fragement: CharityDetailFragment)
}
