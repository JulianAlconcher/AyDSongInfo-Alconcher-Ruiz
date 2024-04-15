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
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale

private const val AUDIO_SCROBBLER = "https://ws.audioscrobbler.com/2.0/"

private const val LASTFM_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

class OtherInfoWindow : Activity() {
    private var textPane1: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane1 = findViewById(R.id.textPane1)
        open(intent.getStringExtra("artistName"))
    }
    private fun getArtistInfo(artistName: String?) {
        // create
        val lastFMAPI = createRetrofitAPI()
        Log.e("TAG", "artistName $artistName")
        Thread {
            val article = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
            var text = ""
            if (article != null) { // exists in db
                text = "[*]" + article.biography
                val urlString = article.articleUrl
                renderView(urlString)
            } else { // get from service
                val callResponse: Response<String>
                try {
                    callResponse = lastFMAPI.getArtistInfo(artistName).execute()
                    Log.e("TAG", "JSON " + callResponse.body())
                    val gson = Gson()
                    val jobj = gson.fromJson(callResponse.body(), JsonObject::class.java)
                    val artist = jobj["artist"].getAsJsonObject()
                    val bio = artist["bio"].getAsJsonObject()
                    val extract = bio["content"]
                    val url = artist["url"]
                    if (extract == null) {
                        text = "No Results"
                    } else {
                        text = extract.asString.replace("\\n", "\n")
                        text = textToHtml(text, artistName)
                        // save to DB  <o/
                        val text2 = text
                        Thread {
                            dataBase!!.ArticleDao().insertArticle(
                                ArticleEntity(
                                    artistName, text2, url.asString
                                )
                            )
                        }.start()
                    }
                    val urlString = url.asString
                    renderView(urlString)
                } catch (e1: IOException) {
                    Log.e("TAG", "Error $e1")
                    e1.printStackTrace()
                }
            }
            Log.e("TAG", "Get Image from $LASTFM_IMAGE_URL")
            val finalText = text
            runOnUiThread {
                Picasso.get().load(LASTFM_IMAGE_URL).into(findViewById<View>(R.id.imageView1) as ImageView)
                textPane1!!.text = Html.fromHtml(finalText)
            }
        }.start()
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
    private fun open(artist: String?) {
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
}