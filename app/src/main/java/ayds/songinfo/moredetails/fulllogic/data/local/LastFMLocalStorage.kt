package ayds.songinfo.moredetails.data.local.lastfm
import ayds.songinfo.moredetails.fulllogic.domain.Article

interface LasfmLocalStorage {
    fun getArticle(artistName: String): Article?
    fun saveArticle(article: Article)
}

class LastfmLocalStorageImpl(
    private val dataBase: ArticleDatabase
): LasfmLocalStorage{

    override fun getArticle(artistName: String): Article? {
        val article = dataBase.ArticleDao().getArticleByArtistName(artistName)
        return if (article != null) {
            Article(article.artistName, article.biography, article.articleUrl, true)
        } else null
    }

    override fun saveArticle(article: Article) {
        if (article.biography != null) {
            Thread {
                dataBase.ArticleDao().insertArticle(
                    ArticleEntity(
                        article.artistName,
                        article.biography,
                        article.articleUrl
                    )
                )
            }.start()
        }
    }
}