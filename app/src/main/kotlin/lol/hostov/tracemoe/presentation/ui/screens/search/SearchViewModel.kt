package lol.hostov.tracemoe.presentation.ui.screens.search

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lol.hostov.tracemoe.data.remote.model.AnilistBody
import lol.hostov.tracemoe.data.remote.model.AnilistResponse
import lol.hostov.tracemoe.data.remote.model.SearchResponse
import lol.hostov.tracemoe.repository.ApiRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: ApiRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    val screenState = MutableStateFlow(SearchScreenState())
    var result by mutableStateOf<SearchResponse?>(null)
        private set
    var details by mutableStateOf<AnilistResponse?>(null)
        private set

    fun updateSearchQuery(newValue: String) {
        screenState.update {
            it.copy(searchQuery = newValue)
        }
    }

    fun searchByUrl(onSuccess: () -> Unit) {
        if (screenState.value.searchByUrlInProgress) return

        screenState.update {
            it.copy(searchByUrlInProgress = true)
        }

        viewModelScope.launch {
            val request = api.searchByUrl(screenState.value.searchQuery)
            val body = request.body()

            if (request.isSuccessful && body != null) {
                val bodyWithoutDuplicates = body.copy(
                    result = body.result.distinctBy { it.anilist.id }
                )

                result = bodyWithoutDuplicates

                val anilistRequest = api.getAnilistInfo(
                    AnilistBody(
                        query = anilistQuery,
                        variables = AnilistBody.Variables(
                            ids = bodyWithoutDuplicates.result.map { it.anilist.id }
                        )
                    )
                )

                if (anilistRequest.isSuccessful) {
                    details = anilistRequest.body()
                }

                onSuccess()
            } else {
                val errorObject = request.errorBody()?.string()

                if (errorObject?.contains("error") == true) {
                    val errorText = JSONObject(errorObject).get("error")

                    Toast.makeText(context, "$errorText", Toast.LENGTH_SHORT).show()
                }
            }

            screenState.update {
                it.copy(searchByUrlInProgress = false)
            }
        }
    }

    fun searchByImage(
        url: String,
        onSuccess: () -> Unit,
        onError: () -> Unit = {},
    ) {
        val uri = Uri.fromFile(File(url))

        searchByImage(uri, onSuccess, onError)
    }

    fun searchByImage(
        uri: Uri,
        onSuccess: () -> Unit,
        onError: () -> Unit = {},
    ) {
        screenState.update {
            it.copy(searchImageInProgress = true)
        }

        viewModelScope.launch {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile("image", ".jpeg", context.cacheDir)

            inputStream?.copyTo(FileOutputStream(file))
            inputStream?.close()

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", "image.jpeg", requestBody)

            val request = withContext(Dispatchers.IO) {
                api.searchByImage(filePart)
            }

            val body = request.body()

            if (request.isSuccessful && body != null) {
                val bodyWithoutDuplicates = body.copy(
                    result = body.result.distinctBy { it.anilist.id }
                )
                result = bodyWithoutDuplicates

                val anilistRequest = api.getAnilistInfo(
                    AnilistBody(
                        query = anilistQuery,
                        variables = AnilistBody.Variables(
                            ids = bodyWithoutDuplicates.result.map { it.anilist.id }
                        )
                    )
                )

                if (anilistRequest.isSuccessful) {
                    details = anilistRequest.body()
                }

                onSuccess()
            } else {
                val errorObject = request.errorBody()?.string()

                if (errorObject?.contains("error") == true) {
                    val errorText = JSONObject(errorObject).get("error")

                    Toast.makeText(context, "$errorText", Toast.LENGTH_SHORT).show()
                }

                onError()
            }

            screenState.update {
                it.copy(searchImageInProgress = false)
            }
        }
    }
}

private val anilistQuery = """
query (${"$"}ids: [Int]) {
    Page(page: 1, perPage: 50) {
      media(id_in: ${"$"}ids, type: ANIME) {
        id
        title {
          native
          romaji
          english
        }
        type
        format
        status
        startDate {
          year
          month
          day
        }
        endDate {
          year
          month
          day
        }
        season
        episodes
        duration
        source
        coverImage {
          large
          medium
        }
        bannerImage
        genres
        synonyms
        studios {
          edges {
            isMain
            node {
              id
              name
              siteUrl
            }
          }
        }
        isAdult
        externalLinks {
          id
          url
          site
        }
        siteUrl
      }
    }
}
""".trimIndent()