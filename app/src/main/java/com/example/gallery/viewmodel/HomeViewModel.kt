package com.example.gallery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.example.gallery.network.GalleryRepository
import com.example.gallery.R
import com.example.gallery.datamodel.PhotoDetail
import com.example.gallery.model.Photo
import com.example.gallery.network.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val apiClient: ApiClient
) : ViewModel() {

    val photosLiveData = MutableLiveData<List<PhotoDetail>?>()

    fun fetchRecentPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = apiClient.galleryApiService.getPhotos(1)
            val list = response.body()?.photos?.photoList
            photosLiveData.postValue(list)
        }
    }

    fun getRecentPhotos(): Flow<PagingData<Photo>> {
        return galleryRepository.getRecentPhotos()
            .flow
            .map { pagingData ->
                pagingData.flatMap {
                    return@flatMap arrayListOf(
                        Photo(
                            it.id.orEmpty(),
                            R.layout.layout_photo_detail,
                            if (it.title?.isEmpty() == true) "No Title" else it.title.orEmpty(),
                            it.url.orEmpty(),
                            it.height,
                            it.width
                        )
                    )
                }
            }
            .cachedIn(viewModelScope)
    }

    fun getSearchedPhotos(text: String): Flow<PagingData<Photo>> {
        return galleryRepository.getSearchedText(text)
            .flow
            .map { pagingData ->
                pagingData.flatMap {
                    return@flatMap arrayListOf(
                        Photo(
                            it.id.orEmpty(),
                            R.layout.layout_photo_detail,
                            if (it.title?.isEmpty() == true) "No Title" else it.title.orEmpty(),
                            it.url.orEmpty(),
                            it.height,
                            it.width
                        )
                    )
                }
            }
            .cachedIn(viewModelScope)
    }
}