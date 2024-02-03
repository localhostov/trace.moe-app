package lol.hostov.tracemoe.data.remote

import core.Constants.DEFAULT_URL
import lol.hostov.tracemoe.data.remote.model.AnilistBody
import lol.hostov.tracemoe.data.remote.model.AnilistResponse
import lol.hostov.tracemoe.data.remote.model.MeResponse
import lol.hostov.tracemoe.data.remote.model.SearchResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("/me")
    suspend fun getMe(): Response<MeResponse>

    @GET("search?anilistInfo")
    suspend fun searchByUrl(
        @Query("url") url: String
    ): Response<SearchResponse>

    @POST("/search?cutBorders&anilistInfo")
    @Multipart
    suspend fun searchByImage(
        @Part file: MultipartBody.Part
    ): Response<SearchResponse>

    @POST("$DEFAULT_URL/anilist")
    suspend fun getAnilistInfo(
        @Body body: AnilistBody
    ): Response<AnilistResponse>
}