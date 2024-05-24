package ayds.songinfo.moredetails.fulllogic.dependencyInjector

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastFM.injector.LastFMInjector
import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorageImpl
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenter
import ayds.songinfo.moredetails.fulllogic.presentation.OtherInfoPresenterImpl


private const val ARTICLE_BD_NAME = "database-article"

object OtherInfoInjector {

    lateinit var presenter: OtherInfoPresenter

    fun initGraph(context: Context) {

        val articleDatabase = Room.databaseBuilder(context, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()

        val articleLocalStorage = OtherInfoLocalStorageImpl(articleDatabase)

        val otherInfoService = LastFMInjector.lastFMService

        val repository = OtherInfoRepositoryImpl(articleLocalStorage, otherInfoService)

        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

        presenter = OtherInfoPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}