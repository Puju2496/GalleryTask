package com.example.gallery.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.gallery.network.ApiClient
import com.example.gallery.paging.RecentPhotoPagingSource
import com.example.gallery.paging.SearchPagingSource
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val apiClient: ApiClient
 ) {
    fun getRecentPhotos() = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            RecentPhotoPagingSource(apiClient.galleryApiService)
        }
    )

    fun getSearchedText(text: String) = Pager(
        PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            SearchPagingSource(apiClient.galleryApiService, text)
        }
    )
}