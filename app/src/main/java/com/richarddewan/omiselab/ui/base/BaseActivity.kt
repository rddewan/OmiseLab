package com.richarddewan.omiselab.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setUpObserver()
        setupView(savedInstanceState)
    }

    protected open fun setUpObserver(){

    }

    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected abstract fun injectDependencies()
}