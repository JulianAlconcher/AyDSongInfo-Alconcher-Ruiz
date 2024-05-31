package ayds.artist.external.newyorktimes.data

import ayds.songinfo.moredetails.fulllogic.domain.Card
import ayds.songinfo.moredetails.fulllogic.domain.CardSource

interface NewYorkTimesToBiographyProxy{
    fun getCard(artistName: String): Card?
}internal class NewYorkTimesToBiographyProxyImpl(private val service: NYTimesService): NewYorkTimesToBiographyProxy {

    override fun getCard(artistName: String): Card?{
        val article = service.getArtistInfo(artistName)
        return articleToCard(article)
    }

    private fun articleToCard(article: NYTimesArticle): Card? {
        return when (article) {
            is NYTimesArticle.NYTimesArticleWithData -> return Card(
                artistName = article.name,
                text = article.info,
                url = article.url,
                source = CardSource.NEW_YORK_TIMES,
                sourceLogoURL = article.sourceLogoUrl,
                isLocallyStored = false
            )
            else -> null
        }
    }
}