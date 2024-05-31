package ayds.artist.external.wikipedia.data

import ayds.songinfo.moredetails.fulllogic.domain.Card
import ayds.songinfo.moredetails.fulllogic.domain.CardSource

interface WikipediaToBiographyProxy {
    fun getCard(artistName: String): Card?
}
internal class WikipediaToBiographyProxyImpl(private val service: WikipediaTrackService): WikipediaToBiographyProxy {

    override fun getCard(artistName: String): Card?{
        val article = service.getInfo(artistName)
        return articleToCard(article)
    }

    private fun articleToCard(article: WikipediaArticle): Card? {
        return when (article) {
            is WikipediaArticle.WikipediaArticleWithData -> return Card(
                artistName = article.name,
                text = article.description,
                url = article.wikipediaURL,
                source = CardSource.WIKIPEDIA,
                sourceLogoURL = article.wikipediaLogoURL,
                isLocallyStored = false
            )
            else -> null
        }
    }
}