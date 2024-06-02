package ayds.songinfo.moredetails.fulllogic.data.local

import ayds.songinfo.moredetails.fulllogic.domain.Card

interface OtherInfoLocalStorage {
    fun getCards(artistName: String): List<Card>
    fun saveCards(cards: List<Card>)
}

internal class OtherInfoLocalStorageImpl(
    private val cardDatabase: CardDatabase,
) : OtherInfoLocalStorage {

    override fun getCards(artistName: String): List<Card> {
        val dbCards = cardDatabase.CardDao().getCardByArtistName(artistName)
        val sourceLogoUrl = ""
        return dbCards.map { Card(it.artistName, it.text, it.url, it.source, sourceLogoUrl, true) }
    }
    override fun saveCards(cards: List<Card>) {
        cards.forEach{
            saveCard(it)
        }
    }
    private fun saveCard(card: Card){
        val sourceLogoUrl = ""
        cardDatabase.CardDao().insertCard(
            CardEntity(
                card.artistName,
                card.text,
                card.url,
                card.source,
                sourceLogoUrl
            )
        )
    }
}