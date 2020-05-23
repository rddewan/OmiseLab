package com.richarddewan.omiselab.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.NullPointerException
import java.net.ConnectException
import javax.inject.Singleton

@Singleton
class NetworkHelper constructor(private val context: Context) {

    fun isNetworkConnected(): Boolean {
        var result  = false
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                result = isCapableNetwork(this, this.activeNetwork)
            }
            else {
                val networkInfo = this.allNetworks
                for (network in networkInfo){
                    if (isCapableNetwork(this,network)) result = true
                }
            }
        }
        return  result
    }

    fun isCapableNetwork(cm: ConnectivityManager, network: Network?): Boolean {
        cm.getNetworkCapabilities(network)?.also {
            if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                return true
            }
            else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                return true
            }
        }
        return  false

    }

    fun castToNetworkError(throwable: Throwable): NetworkError {
        val defaultNetworkError = NetworkError()
        try {

            if (throwable is ConnectException) return NetworkError(0, "0")
            if (throwable is HttpException) return NetworkError(throwable.code(),"0")
            if (throwable !is HttpException) return defaultNetworkError

            val error = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .fromJson(throwable.response().errorBody()?.string(), NetworkError::class.java)

            return NetworkError(throwable.code(), error.statusCode, error.message)

        } catch (e: IOException) {
            Timber.e(e.toString())
        } catch (e: JsonSyntaxException) {
            Timber.e(e.toString())
        } catch (e: NullPointerException) {
            Timber.e(e.toString())
        }
        return defaultNetworkError
    }
}