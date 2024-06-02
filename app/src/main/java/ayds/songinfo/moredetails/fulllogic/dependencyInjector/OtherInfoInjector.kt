package ayds.songinfo.moredetails.fulllogic.dependencyInjector

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import ayds.artist.external.lastFM.LastFmInjector
import ayds.songinfo.moredetails.fulllogic.data.CardBroker
import ayds.songinfo.moredetails.fulllogic.data.CardRepository
import ayds.songinfo.moredetails.fulllogic.data.CardRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.local.CardDatabase
import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.fulllogic.presentation.CardDescriptionHelperImpl
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenterImpl

private const val ARTICLE_BD_NAME = "database-article"

object OtherInfoInjector {

    lateinit var repository: CardRepository

    fun initRepository(context: Context) {
        val database = databaseBuilder(context, CardDatabase::class.java, "database-name-thename").build()

        val lastfmLocalStorage = OtherInfoLocalStorageImpl(database)

        val broker = CardBroker()

        repository = CardRepositoryImpl(lastfmLocalStorage, lastFMArticleService)
    }
}