package ayds.songinfo.moredetails.fulllogic.domain

data class Card(
    val artistName: String,
    val text: String?,
    val url: String,
    val source: CardSource,
    val sourceLogoURL : String,
    var isLocallyStored: Boolean = false
)

enum class CardSource(val displayName: String) {
    LASTFM("LastFM"),
    WIKIPEDIA("WIKIPEDIA"),
    NEW_YORK_TIMES("NEWYORKTIMES"),
}