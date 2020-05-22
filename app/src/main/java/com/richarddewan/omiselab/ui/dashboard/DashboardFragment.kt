package com.richarddewan.omiselab.ui.dashboard


import android.view.View
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.di.component.FragmentComponent
import com.richarddewan.omiselab.ui.base.BaseFragment

class DashboardFragment : BaseFragment<DashboardViewModel>() {

    override fun layoutProvider(): Int = R.layout.fragment_dashboard

    override fun setupView(view: View) {

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObserver() {
        super.setupObserver()
    }
}
