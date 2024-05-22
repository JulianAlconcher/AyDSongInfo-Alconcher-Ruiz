package ayds.songinfo.moredetails.fulllogic.data.local

import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.data.local.ArticleEntity
import ayds.artist.external.lastFM.data.ArtistBiography

interface OtherInfoLocalStorage {
    fun getArticle(artistName: String): ArtistBiography?
    fun insertArtist(artistBiography: ArtistBiography)
}

internal class OtherInfoLocalStorageImpl(
    private val articleDatabase: ArticleDatabase,
) : OtherInfoLocalStorage {

    override fun getArticle(artistName: String): ArtistBiography? {
        val artistEntity = articleDatabase.ArticleDao().getArticleByArtistName(artistName)
        return artistEntity?.let {
            ArtistBiography(artistName, artistEntity.biography, artistEntity.articleUrl)
        }
    }

    override fun insertArtist(artistBiography: ArtistBiography) {
        articleDatabase.ArticleDao().insertArticle(
            ArticleEntity(
                artistBiography.artistName, artistBiography.biography, artistBiography.articleUrl
            )
        )
    }
}