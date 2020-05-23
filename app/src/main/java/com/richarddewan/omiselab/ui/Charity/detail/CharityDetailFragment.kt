package com.richarddewan.omiselab.ui.Charity.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.di.component.FragmentComponent
import com.richarddewan.omiselab.ui.base.BaseFragment
import kotlinx.android.synthetic.main.charity_detail_fragment.*

class CharityDetailFragment : BaseFragment<CharityDetailViewModel>() {

    companion object {
        fun newInstance() = CharityDetailFragment()
    }



    override fun layoutProvider(): Int = R.layout.charity_detail_fragment

    override fun setupView(view: View) {
        val args  = CharityDetailFragmentArgs.fromBundle(requireArguments())
        txtId.text = args.id.toString()
        txtName.text = args.name.toString()
        Glide.with(this)
            .load(args.logoUrl)
            .into(imgLogo)

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)

    }

}
