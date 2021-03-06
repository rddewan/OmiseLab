package com.richarddewan.omiselab.data.repository

import com.richarddewan.omiselab.data.remote.NetworkService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharityRepository @Inject constructor(private val networkService: NetworkService) {

    fun getCharity() = networkService.getCharity()
}