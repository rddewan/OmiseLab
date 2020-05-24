package com.richarddewan.omiselab.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.richarddewan.omiselab.OmiseApplication
import com.richarddewan.omiselab.di.component.ActivityComponent
import com.richarddewan.omiselab.di.component.DaggerActivityComponent
import com.richarddewan.omiselab.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        viewModel.onCreate()
        setUpObserver()
        setupView(savedInstanceState)
    }

    protected open fun setUpObserver(){

    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    private fun buildActivityComponent() =
        DaggerActivityComponent.builder()
            .applicationComponent((application as OmiseApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

}