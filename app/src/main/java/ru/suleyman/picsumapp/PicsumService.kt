package ru.suleyman.picsumapp

import retrofit2.http.GET
import retrofit2.http.Query
import ru.suleyman.picsumapp.model.ImageModel

interface PicsumService {

    @GET("list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int) : MutableList<ImageModel>

}