package ayds.songinfo.moredetails.fulllogic.data


import ayds.artist.external.lastFM.LastFmBiography
import ayds.artist.external.lastFM.LastFmService
import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource
import ayds.songinfo.moredetails.fulllogic.domain.OtherInfoRepository

internal class OtherInfoRepositoryImpl(
    private val otherInfoLocalStorage: OtherInfoLocalStorage,
    private val lastFmService: LastFmService,
) : OtherInfoRepository {

    override fun getCard(artistName: String): Card {
        val dbCard = otherInfoLocalStorage.getCard(artistName)

        val card: Card

        if (dbCard != null) {
            card = dbCard.apply { markItAsLocal() }
        } else {
            card = lastFmService.getArticle(artistName).toCard()
            if (card.text.isNotEmpty()) {
                otherInfoLocalStorage.insertCard(card)
            }
        }
        return card
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }
}

private fun LastFmBiography.toCard() =
    Card(artistName, biography, articleUrl, CardSource.LAST_FM)