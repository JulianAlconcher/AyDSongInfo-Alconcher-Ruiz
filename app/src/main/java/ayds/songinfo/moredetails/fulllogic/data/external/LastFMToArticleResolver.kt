import ayds.songinfo.moredetails.fulllogic.domain.Article
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.*

private const val URL = "url"
private const val CONTENT = "content"
private const val ARTIST = "artist"
private const val BIO = "bio"


interface LastfmToArticleResolver {
    fun getArticleFromExternalData(serviceData: String, artistName: String): Article
}

class JsonToArticleResolver: LastfmToArticleResolver{

    override fun getArticleFromExternalData(
        serviceData: String,
        artistName: String
    ): Article {
        val jobj = getJson(serviceData)
        val artist = getArtist(jobj)
        val text: String? = getBioText(artist, artistName)
        val url = getUrl(artist)
        return Article(artistName, text, url, false)
    }

    private fun getJson(
        serviceData: String,
    ): JsonObject {
        val gson = Gson()
        return gson.fromJson(serviceData, JsonObject::class.java)
    }

    private fun getUrl(artist: JsonObject) = artist[URL].asString


    private fun getBioText(artist : JsonObject, artistName: String): String? {
        val bio = getBio(artist)
        val extract = getBioContent(bio)
        val text = if (extract != null) {
            getTextFromBioContent(extract, artistName)
        } else
            null
        return text
    }

    private fun getTextFromBioContent(
        extract: JsonElement,
        artistName: String
    ): String {
        var text = extract.asString.replace("\\n", "\n")
        text = textToHtml(text, artistName)
        return text
    }

    private fun getBioContent(bio: JsonObject): JsonElement? =
        bio[CONTENT]

    private fun getBio(artist: JsonObject): JsonObject =
        artist[BIO].getAsJsonObject()

    private fun getArtist(jobj: JsonObject): JsonObject =
        jobj[ARTIST].getAsJsonObject()

    private fun textToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append("</font></div></html>")
        return builder.toString()
    }
}