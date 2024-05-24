package ayds.songinfo.moredetails.fulllogic.data

import ayds.artist.external.lastFM.data.ArtistBiography
import ayds.artist.external.lastFM.data.LastfmArticleService
import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.OtherInfoRepository

internal class OtherInfoRepositoryImpl(
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val otherInfoService: LastfmArticleService
) : OtherInfoRepository {

    override fun getArtistInfo(artistName: String): ArtistBiography {
        val dbArticle = otherInfoLocalStorage.getArticle(artistName)

        val artistBiography: ArtistBiography

        if (dbArticle != null) {
            artistBiography = dbArticle.apply { markItAsLocal() }
        } else {
            artistBiography = otherInfoService.getArticle(artistName)
            if (artistBiography.biography.isNotEmpty()) {
                otherInfoLocalStorage.insertArtist(artistBiography)
            }
        }
        return artistBiography
    }

    private fun ArtistBiography.markItAsLocal() {
        isLocallyStored = true
    }
}