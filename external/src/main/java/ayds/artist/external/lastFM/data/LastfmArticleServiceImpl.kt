package ayds.artist.external.lastFM.data

import java.io.IOException

interface LastfmArticleService {
    fun getArticle(artistName: String): ArtistBiography
}
class LastfmArticleServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver
) : LastfmArticleService {

    override fun getArticle(artistName: String): ArtistBiography {

        var artistBiography = ArtistBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            artistBiography = lastFMToArtistBiographyResolver.map(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistBiography
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

}