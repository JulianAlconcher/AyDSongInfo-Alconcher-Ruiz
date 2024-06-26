package ayds.songinfo.moredetails.presenter

import ayds.artist.external.lastFM.LastFmBiography
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyDescriptionHelper
import ayds.songinfo.moredetails.fulllogic.presentation.ArtistBiographyDescriptionHelperImpl
import org.junit.Assert
import org.junit.Test


class LastFmBiographyDescriptionHelperTest {

    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper =
        ArtistBiographyDescriptionHelperImpl()

    @Test
    fun `on local stored artist should return biography`() {
        val lastFmBiography = LastFmBiography("artist", "biography", "url", true)

        val result = artistBiographyDescriptionHelper.getDescription(lastFmBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">[*]biography</font></div></html>",
            result
        )
    }

    @Test
    fun `on no local stored artist should return biography`() {
        val lastFmBiography = LastFmBiography("artist", "biography", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(lastFmBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography</font></div></html>",
            result
        )
    }
    @Test
    fun `should remove apostrophes`() {
        val lastFmBiography = LastFmBiography("artist", "biography'n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(lastFmBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography n</font></div></html>",
            result
        )
    }

    @Test
    fun `should fix on double slash`() {
        val lastFmBiography = LastFmBiography("artist", "biography\\n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(lastFmBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }

    @Test
    fun `should map break lines`() {
        val lastFmBiography = LastFmBiography("artist", "biography\n", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(lastFmBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }
    @Test
    fun `should set artist name bold`() {
        val lastFmBiography = LastFmBiography("artist", "biography artist", "url", false)

        val result = artistBiographyDescriptionHelper.getDescription(lastFmBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography <b>ARTIST</b></font></div></html>",
            result
        )
    }

}