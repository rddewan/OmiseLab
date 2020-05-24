package com.richarddewan.omiselab.data.repository

import com.richarddewan.omiselab.data.remote.NetworkService
import com.richarddewan.omiselab.data.remote.request.DonationRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DonationRepository @Inject constructor(private val networkService: NetworkService) {

    fun addDonation(donationRequest: DonationRequest) = networkService.addDonation(donationRequest)

}