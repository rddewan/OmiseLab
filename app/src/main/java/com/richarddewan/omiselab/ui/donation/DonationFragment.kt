package com.richarddewan.omiselab.ui.donation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer
import com.richarddewan.omiselab.BuildConfig
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.data.remote.request.DonationRequest
import com.richarddewan.omiselab.di.component.FragmentComponent
import com.richarddewan.omiselab.ui.base.BaseFragment
import com.richarddewan.omiselab.ui.donation.success.DonationSuccessFragment
import com.richarddewan.omiselab.util.validate.Validator
import kotlinx.android.synthetic.main.fragment_donation.*
import org.jetbrains.anko.support.v4.alert
import timber.log.Timber
import java.util.regex.Pattern

class DonationFragment : BaseFragment<DonationViewModel>() {
    companion object{
        private const val RC_IMAGE_CAPTURE = 1
        private const val NUMBER_REGEX = "[0-9]+"
    }

    private lateinit var pattern: Pattern
    private lateinit var firebaseVisionImage: FirebaseVisionImage
    private lateinit var firebaseVisionTextRecognizer: FirebaseVisionTextRecognizer

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun layoutProvider(): Int = R.layout.fragment_donation

    override fun setupView(view: View) {
        pattern = Pattern.compile(NUMBER_REGEX)
        firebaseVisionTextRecognizer = FirebaseVision.getInstance().onDeviceTextRecognizer

        btnSubmit.setOnClickListener {
            prepareSendDonation()
        }

        btnScanCard.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
                intent.resolveActivity(requireContext().packageManager)?.also {
                    startActivityForResult(intent, RC_IMAGE_CAPTURE)
                }
            }
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

        })

        viewModel.donationResponse.observe(viewLifecycleOwner, Observer {
            if (it.success){
                findNavController().navigate(DonationFragmentDirections.actionNavigationDonationToNavigationDonationSuccess())
            }
            else {
                showErrorDialog(it.errorMessage)
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
            !Validator.validateEmptyField(txtExpMonth.text.toString()) -> {
                textInputLayoutExpMonth.error = "Expiry month cannot be empty."
            }
            !Validator.validateEmptyField(txtExpYear.text.toString()) -> {
                textInputLayoutExpYear.error = "Expiry year cannot be empty."
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

    /*
    show error dialog with parameter as res id
     */
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

    /*
    show error dialog with parameter as string
     */
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

    /*
    clear the error from text layout
     */
    private fun clearFiledError(){
        textInputLayoutName.error = ""
        textInputLayoutCard.error = ""
        textInputLayoutExpMonth.error = ""
        textInputLayoutExpYear.error = ""
        textInputLayoutCvv.error = ""
        textInputLayoutCard.error = ""
    }

    /*
    firebase ML extract card and exp date from image capture
     */
    private fun getTextFromImageBitmap(bitmap: Bitmap){
        firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap)
        firebaseVisionTextRecognizer.processImage(firebaseVisionImage)
            .addOnSuccessListener {result ->
                var matcher: Boolean

                for (block in result.textBlocks){
                    //remove white space from block of text
                    val textBlock = block.text.replace(" ", "")
                    //split text if contain new line
                    val line = textBlock.split("\n")
                    //loop card as it's a array after we split the text
                    for (text in line){

                        //we only want to match the card regex if data contains number only
                        if (pattern.matcher(text).matches()){
                            matcher = Validator.matchCreditCard(text)
                            if (matcher){
                                //set card text
                                txtCardNumber.setText(text)
                                //get card vendor
                                val vendor = Validator.getCreditCardVendor(text)
                            }
                        }
                        //extract expiry month and year from card
                        else {
                            try {
                                if (text.contains("/")){
                                    val monthYear = text.split("/")
                                    if (pattern.matcher(monthYear[0]).matches()){
                                        txtExpMonth.setText(monthYear[0])
                                    }
                                    if (pattern.matcher(monthYear[1]).matches()){
                                        txtExpYear.setText(monthYear[1])
                                    }
                                }
                            }
                            catch (e: Exception){
                                Timber.e(e)
                            }

                        }
                    }

                }

            }
            .addOnFailureListener {
                showErrorDialog(it.message.toString())
                Timber.e(it)
            }
    }

    /*
    activity result on image capture
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_IMAGE_CAPTURE){
            if (resultCode == Activity.RESULT_OK){
                val bitmap = data?.extras?.get("data") as Bitmap

                getTextFromImageBitmap(bitmap)
            }
        }
    }

}
