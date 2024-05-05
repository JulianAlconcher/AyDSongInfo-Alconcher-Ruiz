
import ayds.songinfo.moredetails.data.local.lastfm.LasfmLocalStorage
import ayds.songinfo.moredetails.domain.ArticleRepository
import ayds.songinfo.moredetails.fulllogic.domain.Article

class ArticleRepositoryImpl(
    private val lastfmArticleService: ayds.songinfo.moredetails.data.external.lastfm.LastfmArticleService,
    private val lastfmLocalStorage: LasfmLocalStorage
): ArticleRepository {

    override fun getArticle(artistName: String): Article?{
        var article = lastfmLocalStorage.getArticle(artistName)
        if (article == null) {
            article = lastfmArticleService.getArticle(artistName)
            if (article?.biography != null){
                lastfmLocalStorage.saveArticle(article)
            }
        }
        return article
    }
}