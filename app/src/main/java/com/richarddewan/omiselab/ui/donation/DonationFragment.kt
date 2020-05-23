package com.richarddewan.omiselab.ui.donation

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.richarddewan.omiselab.BuildConfig
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.data.remote.request.DonationRequest
import com.richarddewan.omiselab.di.component.FragmentComponent
import com.richarddewan.omiselab.ui.base.BaseFragment
import com.richarddewan.omiselab.util.validate.Validator
import kotlinx.android.synthetic.main.fragment_donation.*
import org.jetbrains.anko.support.v4.alert

class DonationFragment : BaseFragment<DonationViewModel>() {

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun layoutProvider(): Int = R.layout.fragment_donation

    override fun setupView(view: View) {
        btnSubmit.setOnClickListener {
            prepareSendDonation()
        }

        btnScanCard.setOnClickListener {

        }

    }

    override fun setupObserver() {
        super.setupObserver()

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            donationProgressBar.visibility = if (it) View.VISIBLE else View.GONE

        })

        viewModel.messageString.observe(viewLifecycleOwner, Observer {
            showErrorDialog(it)
        })

        viewModel.messageStringId.observe(viewLifecycleOwner, Observer {
            showIntErrorDialog(it)
        })

        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(DonationFragmentDirections.actionNavigationDonationToDonationSuccessFragment())
            }
        })
    }

    private fun prepareSendDonation() {

        clearFiledError()

        when {
            !Validator.validateEmptyField(txtName.text.toString()) -> {
                textInputLayoutName.error = "Card holder name cannot be empty."
            }
            !Validator.validateEmptyField(txtCardNumber.text.toString()) -> {
                textInputLayoutCard.error = "Card number cannot be empty."
            }
            !Validator.validateEmptyField(txtExpDate.text.toString()) -> {
                textInputLayoutExpDate.error = "Expiry date cannot be empty."
            }
            !Validator.validateEmptyField(txtExpCvv.text.toString()) -> {
                textInputLayoutCvv.error = "CVV cannot be empty."
            }
            !Validator.validateEmptyField(txtAmount.text.toString()) -> {
                textInputLayoutAmount.error = "Amount cannot be empty."
            }
            !Validator.checkValidCard(txtCardNumber.text.toString()) -> {
                textInputLayoutCard.error = "Sorry the card number is not valid."
            }
            else -> {
                sendDonation(
                    DonationRequest(
                        txtName.text.toString(),
                        BuildConfig.TOKEN,
                        txtAmount.text.toString().toDouble()
                    )
                )
            }
        }
    }

    private fun sendDonation(donationRequest: DonationRequest) {
        viewModel.sendDonation(donationRequest)
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

    private fun clearFiledError(){
        textInputLayoutName.error = ""
        textInputLayoutCard.error = ""
        textInputLayoutExpDate.error = ""
        textInputLayoutCvv.error = ""
        textInputLayoutCard.error = ""
    }

}
