package ayds.songinfo.moredetails.fulllogic.data

import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.Card

interface CardRepository
internal class CardRepositoryImpl(
    private val localStorage: OtherInfoLocalStorage,
    private val broker: CardBroker
) : CardRepository {

    fun getCards(artistName: String): List<Card> {
        var cards = localStorage.getCards(artistName)
        if (cards.isEmpty()) {
            cards = broker.getCards(artistName)
            try {
                if (cards.isNotEmpty()) {
                    localStorage.saveCards(cards)
                }
            }catch (_: Exception) {

            }
        }
        return cards
    }

}