package ayds.songinfo.moredetails.fulllogic.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import ayds.songinfo.moredetails.fulllogic.domain.CardSource

@Database(entities = [CardEntity::class], version = 1)
abstract class CardDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
}

@Entity
data class CardEntity(
    @PrimaryKey
    val artistName: String,
    val text: String,
    val url: String,
    val source: CardSource,
    val sourceLogoURL : String,
    var isLocallyStored: Boolean = false
)

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(article: CardEntity)

    @Query("SELECT * FROM CardEntity WHERE artistName LIKE :artistName LIMIT 1")
    fun getCardByArtistName(artistName: String): List<CardEntity>

}