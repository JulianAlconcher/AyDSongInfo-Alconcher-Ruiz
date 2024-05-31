package ayds.songinfo.moredetails.fulllogic.domain

import ayds.songinfo.moredetails.domain.Card


interface OtherInfoRepository {
    fun getCard(artistName: String): Card
}