package gabrielhernandes.io.newsapp.db

import androidx.room.*
import gabrielhernandes.io.newsapp.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Delete
    suspend fun delete(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article): Long


}