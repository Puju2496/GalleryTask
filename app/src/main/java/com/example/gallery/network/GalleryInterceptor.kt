package com.example.gallery.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class GalleryInterceptor : Interceptor {

    companion object {
        private const val API_KEY = "api_key"
        private const val FORMAT = "format"
        private const val NO_JSON = "nojsoncallback"
        private const val EXTRAS = "extras"

        private const val KEY = "6f102c62f41998d151e5a1b48713cf13"
        private const val JSON = "json"
        private const val NO_JSON_VALUE = "1"
        private const val URL = "url_s"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl = request.url.newBuilder().apply {
            addQueryParameter(API_KEY, KEY)
            addQueryParameter(FORMAT, JSON)
            addQueryParameter(NO_JSON, NO_JSON_VALUE)
            addQueryParameter(EXTRAS, URL)
        }.build()

        val newRequest = request.newBuilder()
            .url(httpUrl)
            .build()

        return chain.proceed(newRequest)
    }
}