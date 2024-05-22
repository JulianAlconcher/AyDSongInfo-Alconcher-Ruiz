package ayds.artist.external.lastFM.data

data class ArtistBiography(
    val artistName: String,
    val biography: String,
    val articleUrl: String,
    var isLocallyStored: Boolean = false
)