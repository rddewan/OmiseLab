package com.richarddewan.omiselab.data.remote.request

import com.google.gson.annotations.SerializedName

data class DonationRequest (
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("amount")
    val amount: Double
)