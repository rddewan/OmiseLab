package com.richarddewan.omiselab.data.remote.response

import com.google.gson.annotations.SerializedName

data class DonationResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("error_message")
    val errorMessage: String
)