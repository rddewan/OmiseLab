package com.richarddewan.omiselab.ui.donation.success


import android.view.View
import androidx.navigation.fragment.findNavController


import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.di.component.FragmentComponent
import com.richarddewan.omiselab.ui.base.BaseFragment
import kotlinx.android.synthetic.main.donation_success_fragment.*

class DonationSuccessFragment : BaseFragment<DonationSuccessViewModel>() {

    companion object {
        fun newInstance() = DonationSuccessFragment()
    }

    override fun layoutProvider(): Int = R.layout.donation_success_fragment

    override fun setupView(view: View) {
        btnSuccessOk.setOnClickListener {
            findNavController().navigate(DonationSuccessFragmentDirections.actionDonationSuccessFragmentToNavigationDonation())
        }

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)

    }

}
