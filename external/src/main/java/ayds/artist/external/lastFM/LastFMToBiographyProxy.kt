package ayds.artist.external.lastFM

import ayds.songinfo.moredetails.fulllogic.domain.Card
import ayds.songinfo.moredetails.fulllogic.domain.CardSource


interface LastFMToBiographyProxy{
    fun getCard(artistName: String): Card?
}
internal class LastFMToBiographyProxyImpl(private val service: LastFmService): LastFMToBiographyProxy {

    override fun getCard(artistName: String): Card?{
        val article = service.getArticle(artistName)
        return articleToCard(article)
    }

    private fun articleToCard(article: LastFmBiography): Card?{
        return Card(
                artistName = article.artistName,
                text = article.biography,
                url = article.articleUrl,
                source = CardSource.LASTFM,
                sourceLogoURL = LASTFM_LOGO,
                isLocallyStored = false
        )
    }
}