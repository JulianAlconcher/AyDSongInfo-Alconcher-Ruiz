package ayds.songinfo.moredetails.repository

import ayds.songinfo.moredetails.fulllogic.data.OtherInfoRepositoryImpl
import ayds.songinfo.moredetails.fulllogic.data.external.OtherInfoService
import ayds.songinfo.moredetails.fulllogic.data.local.OtherInfoLocalStorage
import ayds.songinfo.moredetails.fulllogic.domain.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.domain.OtherInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OtherInfoRepositoryTest {
    private val otherInfoLocalStorage: OtherInfoLocalStorage = mockk()
    private val otherInfoService: OtherInfoService = mockk()

    private val otherInfoRepository: OtherInfoRepository =
        OtherInfoRepositoryImpl(otherInfoLocalStorage,otherInfoService)

    @Test
    fun `given existing article should return artist bio and mark it as local`() {
        val artistBiography =
            ArtistBiography("artistName", "content", "url", false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns artistBiography

        val result = otherInfoRepository.getArtistInfo("artistName")

        assertEquals(artistBiography,result)
        assertTrue(artistBiography.isLocallyStored)
    }
    @Test
    fun `given non existing article in DB, should return artist bio and mark it as local with bio`() {
        val artistBiography =
            ArtistBiography("artistName", "content", "url", false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns null
        every { otherInfoService.getArticle("artistName") } returns artistBiography

        val result = otherInfoRepository.getArtistInfo("artistName")

        assertEquals(artistBiography,result)
        assertFalse(artistBiography.isLocallyStored)
        verify { otherInfoLocalStorage.insertArtist(artistBiography) }
    }
    @Test
    fun `given non existing article in DB, should return artist bio and mark it as local without bio`() {
        val artistBiography =
            ArtistBiography("artistName", "", "url", false)
        every { otherInfoLocalStorage.getArticle("artistName") } returns null
        every { otherInfoService.getArticle("artistName") } returns artistBiography

        val result = otherInfoRepository.getArtistInfo("artistName")

        assertEquals(artistBiography,result)
        assertFalse(artistBiography.isLocallyStored)
        verify(inverse = true) { otherInfoLocalStorage.insertArtist(artistBiography) }
    }
}