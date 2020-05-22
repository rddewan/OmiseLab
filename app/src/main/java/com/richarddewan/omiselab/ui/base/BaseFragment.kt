package com.richarddewan.omiselab.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.richarddewan.omiselab.OmiseApplication
import com.richarddewan.omiselab.di.component.DaggerFragmentComponent
import com.richarddewan.omiselab.di.component.FragmentComponent
import com.richarddewan.omiselab.di.module.FragmentModule
import javax.inject.Inject

abstract class BaseFragment<VM: BaseViewModel>: Fragment() {

    @Inject
    lateinit var viewModel: VM


    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
        setupObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutProvider(),container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    protected open fun setupObserver(){

    }

    @LayoutRes
    protected abstract fun layoutProvider(): Int

    protected abstract fun setupView(view: View)

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)

    protected fun buildFragmentComponent() =
        DaggerFragmentComponent.builder()
            .applicationComponent((requireContext().applicationContext as OmiseApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()

}