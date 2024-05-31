package ayds.songinfo.moredetails.fulllogic.dependencyInjector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastFM.LastFmInjector
import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.local.CardDatabase
import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.fulllogic.presentation.CardDescriptionHelperImpl
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenterImpl

private const val ARTICLE_BD_NAME = "database-article"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {

        LastFmInjector.init()

        val cardDatabase =
            Room.databaseBuilder(context, CardDatabase::class.java, ARTICLE_BD_NAME).build()


        val articleLocalStorage = OtherInfoLocalStorageImpl(cardDatabase)

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, LastFmInjector.lastFmService)

        val artistBiographyDescriptionHelper = CardDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}