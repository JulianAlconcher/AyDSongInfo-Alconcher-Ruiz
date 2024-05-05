package ayds.songinfo.moredetails.fulllogic.domain

data class Article(
    val artistName: String,
    val biography: String?,
    val articleUrl: String,
    val isLocallyStored: Boolean
)