package ayds.songinfo.moredetails.fulllogic.domain

import ayds.artist.external.lastFM.data.ArtistBiography

interface OtherInfoRepository {
    fun getArtistInfo(artistName: String): ArtistBiography
}