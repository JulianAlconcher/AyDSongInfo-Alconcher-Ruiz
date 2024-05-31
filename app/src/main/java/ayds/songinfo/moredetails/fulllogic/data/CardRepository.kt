package ayds.songinfo.moredetails.fulllogic.data

import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.Card

interface CardRepository
internal class CardRepositoryImpl(
    private val lastfmLocalStorage: OtherInfoLocalStorage,
    private val broker: CardBroker
) : CardRepository {

    override fun getCards(artistName: String): List<Card> {
        var cards = lastfmLocalStorage.getCards(artistName)
        if (cards.isEmpty()) {
            cards = broker.getCards(artistName)
            try {
                if (cards.isNotEmpty()) {
                    lastfmLocalStorage.saveCards(cards)
                }
            }catch (e: Exception) {

            }
        }
        return cards
    }

}