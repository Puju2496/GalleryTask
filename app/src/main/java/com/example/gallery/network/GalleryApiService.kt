package com.example.gallery.network

import com.example.gallery.datamodel.Photos
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GalleryApiService {

    @GET("?method=flickr.photos.getRecent&per_page=20")
    suspend fun getPhotos(@Query("page", encoded = true) page: Int): Response<Photos>

    @GET("?method=flickr.photos.search")
    suspend fun getSearchResult(@Query("text", encoded = true) text: String): Response<Photos>
}