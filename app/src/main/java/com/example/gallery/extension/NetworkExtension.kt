package com.example.gallery.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

val Context.isConnected: Boolean
get() {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capability = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

    return when {
        capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}