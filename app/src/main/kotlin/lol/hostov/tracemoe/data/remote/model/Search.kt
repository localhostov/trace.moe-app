package lol.hostov.tracemoe.data.remote.model

import androidx.compose.runtime.Immutable

@Immutable
data class SearchResponse(
    val frameCount: Int,
    val error: String,
    val result: List<SearchResponseItem>
)

data class SearchResponseItem(
    val anilist: AnilistInfo,
    val filename: String,
    val episode: String?,
    val from: Float,
    val to: Float,
    val similarity: Float,
    val video: String,
    val image: String,
)

@Immutable
data class AnilistInfo(
    val id: Int,
    val idMal: Int,
    val title: AnilistResponse.MediaItem.Title,
    val synonyms: List<String>,
    val isAdult: Boolean
)
