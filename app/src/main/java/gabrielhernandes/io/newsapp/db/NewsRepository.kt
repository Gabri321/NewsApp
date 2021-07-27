package gabrielhernandes.io.newsapp.db

import gabrielhernandes.io.newsapp.model.Article

class NewsRepository(private val db: ArticleDatabase) {

    fun getAll(): List<Article> = db.getArticleDao().getAll()


    suspend fun updateInsert(article: Article) {
        db.getArticleDao().updateInsert(article)
    }


    suspend fun delete(article: Article) {
        db.getArticleDao().delete(article)
    }


}