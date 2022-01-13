package com.example.gallery.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gallery.datamodel.PhotoDetail
import com.example.gallery.network.GalleryApiService

class RecentPhotoPagingSource(private val galleryApiService: GalleryApiService) : PagingSource<Int, PhotoDetail>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoDetail>): Int? = null


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDetail> {
        return try {
            val key = params.key ?: 1
            val response = galleryApiService.getPhotos(key)
            LoadResult.Page(
                data = response.body()?.photos?.photoList.orEmpty(),
                prevKey = null,
                nextKey = key + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}