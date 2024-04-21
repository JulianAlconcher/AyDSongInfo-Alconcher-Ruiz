package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale

private const val AUDIO_SCROBBLER = "https://ws.audioscrobbler.com/2.0/"

private const val LASTFM_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

private const val URL = "url"
private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"

class OtherInfoWindow : Activity() {
    private var textPane1: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane1 = findViewById(R.id.textPane1)
        val artistName = intent.getStringExtra("artistName")
        if(artistName != null)
            open(artistName)
    }
    private fun getArtistInfo(artistName: String) {
        val lastFMAPI = createRetrofitAPI()
        Log.e("TAG", "artistName $artistName")
        Thread {
            val article = getArticleFromDB(artistName)
            var text = ""
            if (article != null) {
                text = "[*]" + article.biography
                val urlString = article.articleUrl
                renderView(urlString)
            } else {
                try {
                    val article = getArticleFromAPI(lastFMAPI, artistName)
                    if(article.biography != null)
                        saveArticle(article)

                    text = article.biography ?: "No Results"
                    renderView(article.articleUrl)
                } catch (e1: IOException) {
                    Log.e("TAG", "Error $e1")
                    e1.printStackTrace()
                }
            }
            Log.e("TAG", "Get Image from $LASTFM_IMAGE_URL")
            val finalText = text
            showText(finalText)
        }.start()
    }

    private fun showText(text: String) {
        runOnUiThread {
            Picasso.get().load(LASTFM_IMAGE_URL)
                .into(findViewById<View>(R.id.imageView1) as ImageView)
            textPane1!!.text = Html.fromHtml(text)
        }
    }
    private fun saveArticle(article: Article) {
        if (article.biography != null) {
            Thread {
                dataBase!!.ArticleDao().insertArticle(
                    ArticleEntity(
                        article.artistName,
                        article.biography,
                        article.articleUrl
                    )
                )
            }.start()
        }
    }
    private fun getArticleFromDB(artistName: String?): Article? {
        val article = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
        return if (article != null) {
            Article(article.artistName, article.biography, article.articleUrl, true)
        } else null
    }
    private fun getArticleFromAPI(lastFMAPI: LastFMAPI,artistName: String):Article{
        val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
        Log.e("TAG", "JSON " + callResponse.body())
        val gson = Gson()
        val jobj = gson.fromJson(callResponse.body(), JsonObject::class.java)
        val artist = jobj[ARTIST].getAsJsonObject()
        val bio = artist[BIO].getAsJsonObject()
        val extract = bio[CONTENT]
        val url = artist[URL]
        var text: String?
        if (extract == null) {
            text = "No Results"
        } else {
            text = extract.asString.replace("\\n", "\n")
            text = textToHtml(text, artistName)
            val text2 = text
        }
        val urlString = url.asString
        return Article(artistName, text, urlString,false)
    }
    private fun createRetrofitAPI():LastFMAPI{
        val retrofit = Retrofit.Builder()
            .baseUrl(AUDIO_SCROBBLER)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return retrofit.create(LastFMAPI::class.java)
    }

    private fun renderView(url:String){
        findViewById<View>(R.id.openUrlButton1).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url))
            startActivity(intent)
        }
    }

    private var dataBase: ArticleDatabase? = null
    private fun open(artist: String) {
        dataBase =
            databaseBuilder(this, ArticleDatabase::class.java, "database-name-thename").build()
        Thread {
            dataBase!!.ArticleDao().insertArticle(ArticleEntity("test", "sarasa", ""))
            Log.e("TAG", "" + dataBase!!.ArticleDao().getArticleByArtistName("test"))
            Log.e("TAG", "" + dataBase!!.ArticleDao().getArticleByArtistName("nada"))
        }.start()
        getArtistInfo(artist)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        fun textToHtml(text: String, term: String?): String {
            val builder = StringBuilder()
            builder.append("<html><div width=400>")
            builder.append("<font face=\"arial\">")
            val textWithBold = text
                .replace("'", " ")
                .replace("\n", "<br>")
                .replace(
                    "(?i)$term".toRegex(),
                    "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
                )
            builder.append(textWithBold)
            builder.append("</font></div></html>")
            return builder.toString()
        }
    }

    internal data class Article(
        val artistName: String,
        val biography: String?,
        val articleUrl: String,
        val isLocallyStored: Boolean
    ) {

    }


}
