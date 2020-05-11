package com.richarddewan.omiselab.data.repository

import com.richarddewan.omiselab.data.remote.NetworkService

class CharityRepository constructor(private val networkService: NetworkService) {

    fun getCharity() = networkService.getCharity()
}