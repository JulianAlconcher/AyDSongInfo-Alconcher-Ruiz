package ayds.songinfo.moredetails.fulllogic.domain


interface OtherInfoRepository {
    fun getCard(artistName: String): Card
    fun getCardList(artistName: String): List<Card>
}