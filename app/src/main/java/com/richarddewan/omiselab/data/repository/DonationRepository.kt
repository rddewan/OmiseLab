package com.richarddewan.omiselab.data.repository

import com.richarddewan.omiselab.data.remote.NetworkService
import com.richarddewan.omiselab.data.remote.request.DonationRequest

class DonationRepository constructor(private val networkService: NetworkService) {

    fun addDonation(donationRequest: DonationRequest) = networkService.addDonation(donationRequest)

}