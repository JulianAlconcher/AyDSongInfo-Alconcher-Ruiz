package ayds.songinfo.moredetails.domain

import ayds.songinfo.moredetails.fulllogic.domain.Article

interface ArticleRepository {
    fun getArticle(artistName: String): Article?
}