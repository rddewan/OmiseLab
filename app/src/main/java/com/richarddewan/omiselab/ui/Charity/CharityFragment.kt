package com.richarddewan.omiselab.ui.Charity


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.data.remote.response.CharityResponse
import com.richarddewan.omiselab.data.remote.response.CharityResponseList
import com.richarddewan.omiselab.di.component.FragmentComponent
import com.richarddewan.omiselab.ui.base.BaseFragment
import com.richarddewan.omiselab.ui.Charity.adaptor.CharityListAdaptor
import kotlinx.android.synthetic.main.fragment_charity.*
import org.jetbrains.anko.support.v4.alert
import timber.log.Timber

class CharityFragment : BaseFragment<CharityViewModel>() {

    private lateinit var charityListAdaptor: CharityListAdaptor
    private var mList:ArrayList<CharityResponseList> = ArrayList()

    override fun layoutProvider(): Int = R.layout.fragment_charity

    override fun setupView(view: View) {

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObserver() {
        super.setupObserver()

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            charityProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.charirtyList.observe(viewLifecycleOwner, Observer {
            if (mList.size <= 0){
                mList.add(CharityResponseList(0, "Ban Khru Noi", "http://rkdretailiq.com/news/img-corporate-baankrunoi.jpg"))
                mList.add(CharityResponseList(1, "Habitat for Humanity Thailand", "http://www.adamandlianne.com/uploads/2/2/1/6/2216267/3231127.gif"))
                mList.add(CharityResponseList(3, "Makhampom", "http://www.makhampom.net/makhampom/ppcms/uploads/UserFiles/Image/Thai/T14Publice/2554/January/Newyear/logoweb.jpg"))
                mList = it.toCollection(mList)
                charityListAdaptor = CharityListAdaptor(requireContext(),mList)
            }
            charityListView.adapter = charityListAdaptor

        })

        viewModel.messageString.observe(viewLifecycleOwner, Observer {
            showErrorDialog(it)
        })

        viewModel.messageStringId.observe(viewLifecycleOwner, Observer {
            showIntErrorDialog(it)
        })
    }

    private fun showIntErrorDialog(resId: Int) {
        alert {
            isCancelable = false
            title = getString(R.string.general_error_title)
            message = getString(resId)
            positiveButton(getString(R.string.btn_ok)) {
                it.dismiss()
            }
        }.show()
    }

    private fun showErrorDialog(msg: String) {
        alert {
            isCancelable = false
            title = getString(R.string.general_error_title)
            message = msg
            positiveButton(getString(R.string.btn_ok)) {
                it.dismiss()
            }
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
    }
}
