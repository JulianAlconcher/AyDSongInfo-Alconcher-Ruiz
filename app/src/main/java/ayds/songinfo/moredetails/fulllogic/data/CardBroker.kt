package ayds.songinfo.moredetails.fulllogic.data

import ayds.artist.external.lastFM.LastFMToBiographyProxy
import ayds.artist.external.newyorktimes.data.NewYorkTimesToBiographyProxy
import ayds.artist.external.wikipedia.data.WikipediaToBiographyProxy
import ayds.songinfo.moredetails.fulllogic.domain.Card

interface CardBroker {
    fun getCards(artistName: String): List<Card>
}

internal class CardBrokerImpl(
    private val lastFMT: LastFMToBiographyProxy,
    private val wikipedia: WikipediaToBiographyProxy,
    private val nyTimes: NewYorkTimesToBiographyProxy
): CardBroker {
    override fun getCards(artistName: String): List<Card> {
        val cards = mutableListOf<Card>()

        val lastfmCard = getLastfmCard(artistName)
        addIfNotNull(cards, lastfmCard)

        val wikipediaCard = getWikipediaCard(artistName)
        addIfNotNull(cards, wikipediaCard)

        val nyTimes = getNYTimesCard(artistName)
        addIfNotNull(cards, nyTimes)

        return cards
    }

    private fun getLastfmCard(artistName: String): Card? {
        return lastFMT.getCard(artistName)
    }

    private fun getWikipediaCard(artistName: String): Card?{
        return wikipedia.getCard(artistName)

    }

    private fun getNYTimesCard(artistName: String): Card?{
        return nyTimes.getCard(artistName)
    }

    private fun addIfNotNull(list:MutableList<Card>, card: Card?){
        card?.let { list.add(it) }
    }
}