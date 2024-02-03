package lol.hostov.tracemoe.data.remote.model

import com.google.gson.annotations.SerializedName

data class AnilistResponse(
    val data: Data
) {
    data class Data(
        @SerializedName("Page")
        val page: Page
    )

    data class Page(
        val media: List<MediaItem>
    )

    data class MediaItem(
        val bannerImage: String,
        val coverImage: CoverImage,
        val duration: Int,
        val endDate: Date,
        val episodes: Int,
        val externalLinks: List<ExternalLinks>,
        val format: String,
        val genres: List<String>,
        val id: Int,
        val isAdult: Boolean,
        val season: String,
        val siteUrl: String,
        val source: String,
        val startDate: Date,
        val status: String,
        val studios: Studios,
        val synonyms: List<String>,
        val title: Title,
        val type: String,
    ) {
        data class CoverImage(
            val medium: String,
            val large: String,
        )

        data class Date(
            val year: Int,
            val month: Int,
            val day: Int,
        )

        data class ExternalLinks(
            val id: Int,
            val site: String,
            val url: String,
        )

        data class Studios(
            val edges: List<Edge>
        ) {
            data class Edge(
                val isMain: Boolean,
                val node: Node
            )

            data class Node(
                val id: Int,
                val name: String,
                val siteUrl: String,
            )
        }

        data class Title(
            val native: String,
            val romaji: String?,
            val english: String?,
            val chinese: String?
        )
    }
}

data class AnilistBody(
    val query: String,
    val variables: Variables
) {
    data class Variables(
        val ids: List<Int>
    )
}