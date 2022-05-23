package ru.mobileup.features.photo.data

import retrofit2.http.GET
import retrofit2.http.Query
import ru.mobileup.features.photo.data.dto.PhotoResponse
import ru.mobileup.features.photo.data.dto.PhotoWrapperResponse

interface PhotoApi {

    @GET("search/photos")
    suspend fun getPhotos(@Query("query") searchQuery: String?): PhotoWrapperResponse

    @GET("photos/random?count=6")
    suspend fun getRandomPhotos(): List<PhotoResponse>
}
