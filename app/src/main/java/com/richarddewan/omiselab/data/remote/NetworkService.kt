package com.richarddewan.omiselab.data.remote

import com.richarddewan.omiselab.data.remote.request.DonationRequest
import com.richarddewan.omiselab.data.remote.response.CharityResponse
import com.richarddewan.omiselab.data.remote.response.DonationResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface NetworkService {

    @Headers("Accept: application/json")
    @GET(Endpoints.CHARITY)
    fun getCharity(): Single<CharityResponse>

    @Headers("Accept: application/json")
    @POST(Endpoints.DONATION)
    fun addDonation(@Body donationRequest: DonationRequest): Single<DonationResponse>


}