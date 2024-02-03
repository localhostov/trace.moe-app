package lol.hostov.tracemoe.data.remote.model

// https://soruly.github.io/trace.moe-api/#/docs?id=me
data class MeResponse(
    val id: String,
    val priority: Byte,
    val concurrency: Int,
    val quota: Int,
    val quotaUsed: Int,
)