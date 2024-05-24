package ayds.artist.external.lastFM.injector

import ayds.artist.external.lastFM.data.LastFMAPI
import ayds.artist.external.lastFM.data.LastFMToArtistBiographyResolver
import ayds.artist.external.lastFM.data.LastFMToArtistBiographyResolverImpl
import ayds.artist.external.lastFM.data.LastfmArticleServiceImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
object LastFMInjector {
    private val lastFMToArtistBiographyResolver: LastFMToArtistBiographyResolver = LastFMToArtistBiographyResolverImpl()
    private val lastFMAPI = getLastFMAPI()

    val lastFMService: LastfmArticleServiceImpl = LastfmArticleServiceImpl(lastFMAPI, lastFMToArtistBiographyResolver)
    private fun getLastFMAPI(): LastFMAPI{
        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return retrofit.create(LastFMAPI::class.java)
    }
}