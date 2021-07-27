package gabrielhernandes.io.newsapp.model

import android.content.Context
import gabrielhernandes.io.newsapp.db.ArticleDatabase
import gabrielhernandes.io.newsapp.db.NewsRepository
import gabrielhernandes.io.newsapp.network.RetrofitInstance
import gabrielhernandes.io.newsapp.presenter.favorite.FavoriteHome
import gabrielhernandes.io.newsapp.presenter.news.NewsHome
import gabrielhernandes.io.newsapp.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db: ArticleDatabase = ArticleDatabase(context)

    private var newsRepository = NewsRepository(db)

    fun getBreakingNews(callback: NewsHome.Presenter) {

        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews("br")

            if (response.isSuccessful) {

                response.body()?.let { newsResponse ->

                    callback.onSuccess(newsResponse)
                }

                callback.onComplete()

            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }


    }

    fun getSearchNews(term: String, callback: SearchHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.searchNews(term)

            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }

                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun saveArticle(article: Article) {
        CoroutineScope(Dispatchers.Main).launch {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticles(callback: FavoriteHome.Presenter) {

        var allArticles: List<Article>

        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll()

            withContext(Dispatchers.Main) {
                callback.onSuccess(allArticles)
            }
        }


    }

    fun delete(article: Article?) {
        CoroutineScope(Dispatchers.Main).launch {
            article?.let { safeArticle ->
                newsRepository.delete(safeArticle)
            }

        }
    }


}