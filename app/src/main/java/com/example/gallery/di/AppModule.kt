package com.example.gallery.di

import android.content.Context
import com.example.gallery.network.ApiClient
import com.example.gallery.network.GalleryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiClient(@ApplicationContext context: Context) = ApiClient(context)

    @Singleton
    @Provides
    fun provideGalleryRepository(apiClient: ApiClient) = GalleryRepository(apiClient)
}