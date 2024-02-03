package lol.hostov.tracemoe.repository

import lol.hostov.tracemoe.data.remote.ApiService
import lol.hostov.tracemoe.data.remote.model.AnilistBody
import lol.hostov.tracemoe.data.remote.model.AnilistResponse
import lol.hostov.tracemoe.data.remote.model.MeResponse
import lol.hostov.tracemoe.data.remote.model.SearchResponse
import okhttp3.MultipartBody
import retrofit2.Response

class ApiRepository(private val service: ApiService) : ApiService {
    override suspend fun getMe(): Response<MeResponse> {
        return service.getMe()
    }

    override suspend fun searchByUrl(url: String): Response<SearchResponse> {
        return service.searchByUrl(url)
    }

    override suspend fun searchByImage(file: MultipartBody.Part): Response<SearchResponse> {
        return service.searchByImage(file)
    }

    override suspend fun getAnilistInfo(body: AnilistBody): Response<AnilistResponse> {
        return service.getAnilistInfo(body)
    }
}