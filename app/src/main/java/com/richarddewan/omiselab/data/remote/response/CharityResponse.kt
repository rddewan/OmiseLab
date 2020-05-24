package com.richarddewan.omiselab.data.remote.response

import com.google.gson.annotations.SerializedName

data class CharityResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val data: List<CharityResponseList>
)