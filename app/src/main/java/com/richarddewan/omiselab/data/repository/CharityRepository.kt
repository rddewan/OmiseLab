package com.richarddewan.omiselab.data.repository

import com.richarddewan.omiselab.data.remote.NetworkService
import javax.inject.Inject

class CharityRepository @Inject constructor(private val networkService: NetworkService) {

    fun getCharity() = networkService.getCharity()
}